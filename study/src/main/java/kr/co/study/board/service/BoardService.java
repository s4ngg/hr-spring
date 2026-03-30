package kr.co.study.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import kr.co.study.board.dto.ReqBoardDTO;
import kr.co.study.board.dto.ResBoardDTO;

/**
 * 게시판 관련 서비스 인터페이스
 *  - 게시판과 관련된 핵심 비즈니스 로직을 정의하는 서비스 계층
 *  - Controller → Service → Repository 흐름에서 서비스 계층을 담당
 *  - 게시판에서 구현해야 할 메서드들을 미리 추상 메서드로 작성해둠.
 */
public interface BoardService {

	/**
	 * 게시글 작성 기능
	 * 
	 * 기능
	 * 1. 작성자 조회
	 * 2. 해당 작성자가 맞다면 Board 엔티티 작성
	 * 3. DB 저장
	 * @param request 사용자가 입력한 글쓰기 데이터
	 * @param writerId 로그인한 회원의 ID
	 */
	void write(ReqBoardDTO request, List<MultipartFile> files, Long writerId);
	/**
	 * 게시글 리스트 조회기능
	 *  -boardType으로 게시판종류를 확인 후 조회
	 *  - 조회된 Board 엔티티를 Response DTO 타입으로 변환
	 * @return
	 */
	
	/**
	 * 게시글 상세보기 조회
	 *  - PK를 기준으로 조건 검색
	 *  - PK로 조건을 주므로 조회 결과는 무조건 1개의 게시글
	 * 
	 * @param id 게시글 PK
	 * @return
	 */
	/**
	 * 게시글 수정 기능
	 *  - id로 게시글 조회
	 *  - 조회된 게시글의 작성자가 로그인한 작성자(id)와 일치하지 않으면
	 *  "작성자만 수정할 수 있습니다." 출력
	 *  - 엔티티 필드 값을 사용자가 수정한 값으로 변경
	 *  	> JPA 더티체킹으로 인해 자동 반영
	 * @param request
	 * @param id
	 */
	
	/**
	 * 게시글 삭제 기능
	 *  - id로 게시글 조회 (findById)
	 *  - 해당하는 게시글이 없을 경우, "삭제할 수 없습니다." 출력
	 *  - 작성자 검증(게시글 작성자와 로그인한 사용자가 일치하는지)
	 *  - 삭제 처리
	 * @param id 게시글의 PK
	 * @param loginUserId 로그인한 유저의 PK
	 */
	 void edit(ReqBoardDTO request, List<MultipartFile> files, Long id);
	 void delete(Long id, Long loginUserId);
	ResBoardDTO getBoardDetail(Long id);
	Page<ResBoardDTO> getBoardList(int page);
	ResBoardDTO getBoardDetailEdit(Long id);

}
