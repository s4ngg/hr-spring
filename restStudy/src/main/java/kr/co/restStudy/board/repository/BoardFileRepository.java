package kr.co.restStudy.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.restStudy.board.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long>{

	/**
	 * 게시글 ID 기준으로 첨부파일 목록 조회
	 * 
	 * 사용 목적
	 *  - 게시글 상세 조회 시 첨부파일 목록 조회
	 *  - 게시글 수정 시 기존 파일 목록 조회(교체/삭제 처리)
	 * @param boardId 게시글 ID(FK)
	 * @return List<BoardFile> 해당 게시글에 연결된 첨부파일 목록
	 */
	List<BoardFile> findAllByBoardId(Long boardId);
}
