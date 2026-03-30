//package kr.co.study.board.serviceimpl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import kr.co.study.board.dto.ResBoardDTO;
//import kr.co.study.board.entity.Board;
//
//public class NoticeServiceImpl {
//
//	
//	
//	@Override
//	List<ResBoardList> getBoardList() {
////		1. 공지사항 게시글 전체 조회
//		List<Board>boardList = boardRepository.findByBoardTypeOrderByIdDesc("NOTICE");
//		
////		2. Entity 타입을 Response DTO 타입으로 변경
//		List<ResBoardDTO> list = new ArrayList<>();
//		ResBoardDTO response = new ResBoardDTO();
//		
//		for(Board b : boardList) {
//			response.setId(b.getId());
//			response.setTitle(b.getTitle());
//			response.setContent(b.getContent());
//			response.setWriterName(b.getWriter().getUserName());
//			response.setCreatedAt(b.getCreatedAt());		}
//		return list;
//	}
//}
//

package kr.co.study.board.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import kr.co.study.board.dto.ReqBoardDTO;
import kr.co.study.board.dto.ResBoardDTO;
import kr.co.study.board.dto.ResBoardFileDTO;
import kr.co.study.board.entity.Board;
import kr.co.study.board.repository.BoardRepository;
import kr.co.study.board.service.BoardFileService;
import kr.co.study.board.service.BoardService;
import kr.co.study.member.entity.Member;
import kr.co.study.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements BoardService {

    private final BoardRepository boardRepository; // 게시판 DB 접근
    private final MemberRepository memberRepository; // 작성자 조회용
    private final BoardFileService boardFileService; // 파일 전용(추가/수정/삭제) 

    @Override
    @Transactional
    public void write(ReqBoardDTO request, List<MultipartFile> files, Long writerId) {
        Member writer = memberRepository.findById(writerId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자"));

        Board board = new Board();
        board.setBoardType("NOTICE");
        board.setCategory(request.getCategory());
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setWriter(writer);
        board.setViewCount(0);

        boardRepository.save(board);
        
        boardFileService.saveFiles(board, files);
    }
	
	
	
	
	
	@Override
	public Page<ResBoardDTO> getBoardList(int page) {
//		0. 페이징 처리 객체
//		 - 매개변수 : page번째 요청, 한 페이지에 3개씩, id기준 내림차순(desc)
		Pageable pageable = PageRequest.of(page, 3, Sort.by("id").descending());

		// 1️⃣ 공지사항 게시글 전체 조회
		Page<Board> boardList = boardRepository.findByBoardTypeOrderByIdDesc("NOTICE", pageable);

		// 2️⃣ Entity → DTO 변환
		List<ResBoardDTO> list = new ArrayList<>();

		for (Board b : boardList) {
			ResBoardDTO response = ResBoardDTO.builder()
			.id(b.getId())
			.category(b.getCategory())
			.title(b.getTitle())
			.content(b.getContent())
			.writerName(b.getWriter().getUserName())
			.createdAt(b.getCreatedAt())
			.build();

			list.add(response);
		}

		// 3️⃣ 응답 객체(Response DTO) 반환
//		 - List<ResBoardDTO> 타입을 Page<ResBoardDTO>타입으로 변환
//		 - 매개변수 : 원본 리스트, 페이징 정보(객체), db에서 조회된 Page 객체의 요소 개수
		return new PageImpl<>(list, pageable, boardList.getTotalElements());
	}
//	동작순서
//  1. 트랜잭션 시작
//		- JPA의 영속성 컨텍스트 생성
//		- 영속성 컨텍스트 : 엔티티의 변경을 감지하고 SQL을 저장하는 공간
//	2. findById 호출
//		- SELECT 실행
//		- 영속성 컨텍스트에 1차 캐시 저장 → 스냅샷 저장소에 저장
//	3. 나머지 메서드의 코드를 실행 (엔티티.setViewCount(5))
//		- 1차 캐시에 변경된 값이 들어감.
//	4. JPA의 flush() 호출
//		- 변경 감지 수행(더티 체킹)
//		- 변경된 값이 있으면 SQL 쿼리문 생성 후 실행
//	5. 최종적으로 종료되며, 트랜잭션 commit 수행
	
		@Override
		@Transactional
		public ResBoardDTO getBoardDetail(Long id) {
			
			// 1. 게시글 조회
			Board board = boardRepository.findById(id).orElse(null);
			
//			2. 조회수 증가
//			 - JPA 더팅체킹으로 인해 update 자동 반영
			board.setViewCount(board.getViewCount()+1);
			
//			3. 첨부파일 조회
			List<ResBoardFileDTO> files = boardFileService.getFiles(board.getId());
//			3. 응답 DTO 변환
			ResBoardDTO response = ResBoardDTO.builder()
									.id(board.getId())
									.title(board.getTitle())
									.content(board.getContent())
									.writerName(board.getWriter().getUserName())
									.createdAt(board.getCreatedAt())
									.viewCount(board.getViewCount())
									.files(files)
									.build();
//			builder패턴의 장점 일관성이 깨지지 않는다.
			
			return response;
			
	}
	
	@Override
	@Transactional
	public ResBoardDTO getBoardDetailEdit(Long id) {
		
		// 1. 게시글 조회
		Board board = boardRepository.findById(id).orElse(null);
		

//		3. 응답 DTO 변환
		ResBoardDTO response = ResBoardDTO.builder()
								.id(board.getId())
								.title(board.getTitle())
								.content(board.getContent())
								.writerName(board.getWriter().getUserName())
								.createdAt(board.getCreatedAt())
								.viewCount(board.getViewCount())
								.build();
//		builder패턴의 장점 일관성이 깨지지 않는다.
		
		return response;
		
	}
	@Override
	@Transactional
	public void edit(ReqBoardDTO request, List<MultipartFile> files, Long id) {
		
		
//		1. 기존 게시글이 존재하는지 조회
		Board board = boardRepository.findById(request.getId()).orElse(null);
		
		if(board != null && !board.getWriter().getId().equals(id)) {
			System.out.println("게시글이 없거나 작성자가 아닙니다.");
		}
//		2. 게시글 수정 반영
		board.setCategory(request.getCategory());
		board.setTitle(request.getTitle());
		board.setContent(request.getContent());
//		3. 파일 처리
		boardFileService.replaceFiles(board, files);
	}
	@Override
	@Transactional
	public void delete(Long id, Long loginUserId) {
//		1. id로 게시글 조회
		Board board = boardRepository.findById(id).orElse(null);
//		2. 해당하는 게시글이 존재하는지 확인 및 작성자 검증
		if(board == null) {
			System.out.println("삭제할 수 없습니다.");
		} else if(!board.getWriter().getId().equals(loginUserId)) {
			System.out.println("삭제 권한이 없습니다.");
		}
		
//		3. 파일 삭제(로컬 + DB)
		boardFileService.deleteFiles(board.getId());
		
//		4. 삭제 처리
		boardRepository.delete(board);
	}


}
