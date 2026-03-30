package kr.co.study.member.service;

import org.springframework.stereotype.Service;

import kr.co.study.member.dto.ReqLoginDTO;
import kr.co.study.member.dto.ReqRegisterDTO;
import kr.co.study.member.dto.ResLoginDTO;
import lombok.RequiredArgsConstructor;

/**
 * 회원 도메인의 핵심 비즈니스 로직을 정의하는 서비스 인터페이스
 * 주로 Controller -> service -> Repository 흐름에서 서비스 계층의 역할을 담당.
 */
@Service
public interface MemberService {

	/**
	 * 회원가입을 처리하는 메서드
	 * 
	 * 기능 
	 *	- 비밀번호 암호화(BCrypt)
	 *	- 아이디/이메일 중복 체크
	 *	- Member 엔티티 생성 및 DB저장
	 *	- 필요한 경우 예외 발생 (ex. MemberException)
	 *
	 * @param request 사용자가 입력한 회원가입 정보를 담는 DTO
	 */
	void register(ReqRegisterDTO request);
	
	/**
	 * 
	 * 기능
	 *  - 해당 아이디를 가진 유저가 있는지 조회(findByUserId)
	 *  - 만약 존재하지 않으면 null 반환
	 *  - 존재한다면 사용자가 입력한 평문 비밀번호로 BCrypt로 검증
	 *  	> 사용자가 입력한 비밀번호가 암호화된 비밀번호와 일치하는지
	 *  - 일치하지 않는다면 null 반환
	 * @param request 사용자가 입력한 로그인 정보(userId, password)
	 */
	
	ResLoginDTO login(ReqLoginDTO request);
}
