package kr.co.restStudy.board.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import kr.co.restStudy.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	@Id // pk 지정
	@GeneratedValue(strategy=GenerationType.IDENTITY) // auto_increment
	private Long id;
//	게시판 종류 구분
//	 - NOTICE : 공지사항
//	 - FREE : 자유게시판
	private String boardType;
	private String category;
	private int  viewCount;
	private String title;
	
	@Lob // 대용량 데이터를 의미
	private String content;
	
	@ManyToOne // (Board 엔티티) N : 1 (Member 엔티티)
	@JoinColumn(name="writer_id") // 실제 테이블의 FK 컬럼명
	private Member writer;
	
	
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	// INSERT 되기 직전에 자동 실행되는 어노테이션
//	저장되기 전 자동 호출
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
//	수정되기 전 자동 호출
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}

