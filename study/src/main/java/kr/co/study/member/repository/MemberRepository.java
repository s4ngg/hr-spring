package kr.co.study.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.study.member.entity.Member;

/**
 * Member 엔티티와 관련된 DB 접근을 담당하는 Repository 인터페이스
 * 	- JpaRepository를 상속받아 기본 CRUD 기능 제공
 * 	- 제공되는 기본 기능 : save(), findById(), findAll(), delete() 등
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
/**
 * 아이디 중복 체크
 *  - JPA 메서드 이름 규칙에 따라 자동으로 쿼리가 생성됨
 *  - SELECT count(*) > 0 FROM member WHERE user_id = ?
 * @param userId 사용자가 회원가입에서 입력한 아이디
 * @return true : 이미 해당 사용자가 userId를 사용중
 * 		   false : 존재하지 않음 (사용 가능)
 */
	boolean existsByUserId(String userId);
	/**
	 * 이메일 중복 체크
	 *  - JPA 메서드 이름 규칙에 따라 자동으로 쿼리가 생성됨
	 *  - SELECT count(*) > 0 FROM member WHERE email = ?
	 * @param email 사용자가 회원가입에서 입력한 이메일
	 * @return true : 이미 해당 사용자가 email을 사용중
	 * 		   false : 존재하지 않음 (사용 가능)
	 */
	
	
	boolean existsByemail(String email);
	
	
	/**
	 * 
	 * userId와 일치하는 회원 조회
	 *  - JPA 메서드 이름 규칙에 따라 자동으로 쿼리가 생성도미
	 *  	> findBy필드명(매개변수)
	 *  	> SELECT * FROM member WHERE user_id = ?
	 *  
	 *  주의할 점
	 *   - 현재 반환 타입은 엔티티(Member)를 받으므로 1개만 반환 가능
	 *   	> 동일한 userId는 존재할 수 없다고 가정(UNIQUE 제약)
	 *   참고
	 *   - 여러 개의 SELECT 결과를 받을 경우 List가 사용됨.
	 *   - SELECT 결과가 없으면 null 반환
	 *   	> 추가로 공부한다면 Optional 찾아보기!
	 */
	
	Member findByUserId(String userId);
}
