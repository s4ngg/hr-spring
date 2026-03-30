package kr.co.study.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.study.board.entity.Board;

/**
 * 게시판 관련 레포지토리
 *  - JPA를 통해 Board 엔티티를 DB와 연결하는 데이터 접근 계층
 *  - save(), findAll() 등 기본 CRUD 기능 자동 제공
 */
public interface BoardRepository extends JpaRepository<Board, Long> {
	
	/**
	 * 전체 게시글을 조회하는 추상 메서드
	 *  - findByBoardType : boardType이 일치하는 게시글을 찾겠다.
	 *  - OrderByIdDesc : id 컬럼을 기준으로 내림차순을 하겠다.
	 *  - 완성되는 쿼리 : SELECT * FROM board
	 *  			   WHERE board_type = "매개변수로 들어온 값"
	 *  				ORDER BY id DESC
	 *  @param boardType
	 *  @return
	 */
	Page<Board> findByBoardTypeOrderByIdDesc(String boardType, Pageable pageable);
	
	
}
