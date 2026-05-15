package kr.co.korea.rag_backend.ai.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import kr.co.korea.rag_backend.ai.dto.DocumentFileType;
import kr.co.korea.rag_backend.ai.dto.UploadDocumentRequest;
import kr.co.korea.rag_backend.ai.dto.UploadDocumentResponse;
import kr.co.korea.rag_backend.global.config.AiProperties;
/**
 * 전체 흐름
 * 	1. 사용자가 파일 업로드하면 Base64 문자열로 파일 정보 전달
 * 	2. 업로드 크기 검사
 * 	3. Base64를 byte[]로 변환
 * 	4. 파일 타입 판별
 * 	5. TXT, DOCX 텍스트 추출
 * 	6. 텍스트 정규화
 * 	7. 문서 아이디 생성
 * 	8. 메모리 캐시 저장
 * 	9. 로컬 파일 저장
 * 	10. 응답 반환
 */
@Service
public class DocumentUploadService {
	// 문서에 있는 텍스트 저장할 파일명
	private static final String CONTENT_FILE_NAME = "content.txt";
	
	// 문서의 부가 정보(파일명, 타입, 청크 수 등)를 저장할 파일명
	private static final String METADATA_FILE_NAME = "metadata.properties";
	
	// 업로드된 문서를 조회하기 위한 메모리 캐시
	//	- ConcurrentHashMap : 요청이 여러개 와도 안정적으로 동작하는 Map
	private final Map<String, StoredDocument> documents =
												new ConcurrentHashMap<>();
	
	private final AiProperties properties;
	private final Path storagePath;
	
	public DocumentUploadService(AiProperties properties) {
		this.properties = properties;
		
		// 설정 파일에 있는 로컬 경로를 절대 경로로 변환
		this.storagePath = Path.of(properties.getDocument().getStoragePath())
								.toAbsolutePath()
								.normalize();
		// 서버가 재실행될 때 저장 경로에 있는 문서들을 다시 메모리에 로딩
		loadStoredDocuments();
	}
    /**
     * 업로드 요청을 처리하고 React가 기대하는 문서 응답을 생성한다.
     *
     * @param request Base64 파일 내용과 파일 메타데이터를 담은 요청
     * @return 문서 식별자, 청크 수, 미리보기 등을 담은 업로드 응답
     * @throws IllegalArgumentException Base64 문자열을 디코딩할 수 없거나 문서 텍스트를 추출할 수 없는 경우
     */
    public UploadDocumentResponse upload(UploadDocumentRequest request) {
    	
    	// 1. Base64 문자열 길이를 기준으로 업로드 파일 크기가 제한을 넘어가는지 검사
        validateUploadSize(request.base64Content());
        
        // 2. Base64 문자열을 byte 배열로 변환
        byte[] decodedBytes = decodeBase64(request.base64Content());
        
        // 3. 파일명과  Content-Type을 보고, TXT인지 DOCX인지 판단.
        DocumentFileType fileType = DocumentFileType.from(request.fileName(), request.contentType());
        
        // 4. 파일 타입에 맞게 텍스트를 추출
        String extractedText = extractText(fileType, decodedBytes);
        
        // 5. 불필요한 공백과 개행 정리
        String normalizedText = normalizeText(extractedText);

        // 6. 문서를 식별하기 위한 고유 아이디 생성
        String documentId = UUID.randomUUID().toString();
        
        // 7. 문서 길이를 기준으로 청크 개수를 계산
        int chunkCount = calculateChunkCount(normalizedText);
        
        // 8. 화면에 보여줄 미리보기 텍스트 생성
        String preview = createPreview(normalizedText);
        
        // 9. 요청에 메타데이터가 없으면 빈 Map 사용
        Map<String, Object> metadata = request.metadata() == null ? Map.of() : request.metadata();
        
        
        //10. 서버 내부에서 사용할 문서 객체 생성
        StoredDocument document = new StoredDocument(
                documentId,
                request.fileName(),
                request.contentType(),
                normalizedText,
                chunkCount
        );
        
        // 11. 메모리 캐시에 저장
        documents.put(documentId, document);
        
        // 12. 서버 재시작 후에도 유지될 수 있게 로컬에 파일 저장
        saveDocument(document);

        // 13. 응답 반환
        return new UploadDocumentResponse(
                documentId,
                request.fileName(),
                request.contentType(),
                chunkCount,
                preview,
                metadata
        );
    }

    /**
     * 업로드된 문서를 식별자로 조회한다.
     *
     * @param documentId 업로드 응답으로 발급한 문서 식별자
     * @return 문서가 존재하면 StoredDocument, 없으면 빈 Optional
     */
    public Optional<StoredDocument> findById(String documentId) {
        StoredDocument cachedDocument = documents.get(documentId);
        if (cachedDocument != null) {
            return Optional.of(cachedDocument);
        }

        Optional<StoredDocument> loadedDocument = loadDocument(documentId);
        loadedDocument.ifPresent(document -> documents.put(document.documentId(), document));
        return loadedDocument;
    }

    /**
     * Base64 문자열을 파일 바이트 배열로 디코딩한다.
     *
     * @param base64Content React FileReader가 생성한 Base64 파일 내용
     * @return 디코딩된 파일 바이트 배열
     * @throws IllegalArgumentException Base64 형식이 올바르지 않은 경우
     */
    public byte[] decodeBase64(String base64Content) {
        try {
            return Base64.getDecoder().decode(base64Content);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("파일 내용을 Base64로 디코딩할 수 없습니다.", exception);
        }
    }

    /**
     * Base64 요청 본문이 서버 고정 업로드 크기 제한을 넘지 않는지 검사한다.
     *
     * @param base64Content React에서 전달한 Base64 파일 내용
     * @throws IllegalArgumentException 업로드 파일 추정 크기가 제한을 초과한 경우
     */
    public void validateUploadSize(String base64Content) {
        if (base64Content == null) {
            return;
        }

        long estimatedBytes = (base64Content.length() * 3L) / 4L;
        long maxUploadBytes = properties.getDocument().getMaxUploadBytes();
        if (estimatedBytes > maxUploadBytes) {
            throw new IllegalArgumentException("업로드 파일 크기가 서버 제한을 초과했습니다.");
        }
    }

    /**
     * 파일 처리 타입에 맞는 방식으로 문서 텍스트를 추출한다.
     *
     * @param fileType 문서 처리 타입
     * @param fileBytes 업로드 파일 바이트 배열
     * @return 요약과 챗봇에 사용할 순수 텍스트
     */
    public String extractText(DocumentFileType fileType, byte[] fileBytes) {
        return switch (fileType) {
            case DOCX -> extractDocxText(fileBytes);
            case TEXT -> new String(fileBytes, StandardCharsets.UTF_8);
        };
    }

    /**
     * Apache POI를 사용해 Word docx 파일의 문단과 표 텍스트를 추출한다.
     *
     * @param fileBytes docx 파일 바이트 배열
     * @return docx 내부에서 추출한 텍스트
     * @throws IllegalArgumentException docx 구조를 읽을 수 없는 경우
     */
    public String extractDocxText(byte[] fileBytes) {
        try (
                ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
                XWPFDocument document = new XWPFDocument(inputStream);
                XWPFWordExtractor extractor = new XWPFWordExtractor(document)
        ) {
            return extractor.getText();
        } catch (IOException exception) {
            throw new IllegalArgumentException("DOCX 문서 텍스트를 추출할 수 없습니다.", exception);
        }
    }

    /**
     * 추출된 문서 텍스트에서 널 문자와 과도한 공백을 정리한다.
     *
     * @param text 파일에서 추출한 원본 텍스트
     * @return 요약과 미리보기에 사용할 정규화된 텍스트
     */
    public String normalizeText(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace("\u0000", "")
                .replace("\r\n", "\n")
                .replaceAll("[ \\t]+", " ")
                .replaceAll("\\n{3,}", "\n\n")
                .trim();
    }

    /**
     * 화면에 표시할 문서 미리보기 문자열을 생성한다.
     *
     * @param text 정규화된 문서 텍스트
     * @return 설정된 길이 이하의 미리보기 문자열
     */
    public String createPreview(String text) {
        int previewLength = properties.getDocument().getPreviewLength();
        if (text == null || text.length() <= previewLength) {
            return text == null ? "" : text;
        }

        return text.substring(0, previewLength);
    }

    /**
     * 업로드된 텍스트를 설정된 chunk 크기 기준으로 환산한다.
     *
     * @param text 업로드 파일에서 추출한 텍스트
     * @return 화면에 표시할 청크 수
     */
    public int calculateChunkCount(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }

        return (int) Math.ceil((double) text.length() / properties.getDocument().getChunkSize());
    }

    /**
     * 업로드 문서 텍스트와 메타데이터를 로컬 파일 시스템에 저장한다.
     *
     * @param document 저장할 업로드 문서
     * @throws IllegalStateException 파일 시스템 저장에 실패한 경우
     */
    public void saveDocument(StoredDocument document) {
        try {
            Path documentPath = storagePath.resolve(document.documentId());
            Files.createDirectories(documentPath);

            Files.writeString(
                    documentPath.resolve(CONTENT_FILE_NAME),
                    document.content(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            Properties storedProperties = new Properties();
            storedProperties.setProperty("documentId", document.documentId());
            storedProperties.setProperty("fileName", document.fileName());
            storedProperties.setProperty("contentType", document.contentType());
            storedProperties.setProperty("chunkCount", String.valueOf(document.chunkCount()));

            try (var outputStream = Files.newOutputStream(
                    documentPath.resolve(METADATA_FILE_NAME),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )) {
                storedProperties.store(outputStream, "uploaded document metadata");
            }
        } catch (IOException exception) {
            throw new IllegalStateException("문서를 로컬 저장소에 저장할 수 없습니다.", exception);
        }
    }

    /**
     * 서버 시작 시 로컬 파일 시스템에 저장된 문서를 메모리 캐시에 로딩한다.
     */
    public void loadStoredDocuments() {
        if (!Files.exists(storagePath)) {
            return;
        }

        try (var stream = Files.list(storagePath)) {
            stream.filter(Files::isDirectory)
                    .map(path -> loadDocument(path.getFileName().toString()))
                    .flatMap(Optional::stream)
                    .forEach(document -> documents.put(document.documentId(), document));
        } catch (IOException exception) {
            throw new IllegalStateException("저장된 문서 목록을 로딩할 수 없습니다.", exception);
        }
    }

    /**
     * 로컬 파일 시스템에서 단일 문서를 읽어온다.
     *
     * @param documentId 로딩할 문서 식별자
     * @return 저장된 문서가 있으면 StoredDocument, 없으면 빈 Optional
     */
    public Optional<StoredDocument> loadDocument(String documentId) {
        Path documentPath = storagePath.resolve(documentId).normalize();
        Path contentPath = documentPath.resolve(CONTENT_FILE_NAME);
        Path metadataPath = documentPath.resolve(METADATA_FILE_NAME);

        if (!documentPath.startsWith(storagePath) || !Files.exists(contentPath) || !Files.exists(metadataPath)) {
            return Optional.empty();
        }

        try {
            Properties storedProperties = new Properties();
            try (var inputStream = Files.newInputStream(metadataPath)) {
                storedProperties.load(inputStream);
            }

            String content = Files.readString(contentPath, StandardCharsets.UTF_8);
            String fileName = storedProperties.getProperty("fileName", "");
            String contentType = storedProperties.getProperty("contentType", "application/octet-stream");
            int chunkCount = Integer.parseInt(storedProperties.getProperty("chunkCount", "0"));

            return Optional.of(new StoredDocument(documentId, fileName, contentType, content, chunkCount));
        } catch (IOException | NumberFormatException exception) {
            throw new IllegalStateException("저장된 문서를 로딩할 수 없습니다.", exception);
        }
    }
	public record StoredDocument(
			String documentId,
			String fileName,
			String contentType,
			String content,
			int chunkCount
	) {
		
	}
}
