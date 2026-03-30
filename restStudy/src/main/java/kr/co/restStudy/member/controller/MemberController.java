package kr.co.restStudy.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import kr.co.restStudy.member.dto.ReqLoginDTO;
import kr.co.restStudy.member.dto.ReqRegisterDTO;
import kr.co.restStudy.member.dto.ResLoginDTO;
import kr.co.restStudy.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name="Member", description="회원 API")
public class MemberController {


	private final MemberService memberService;



	@PostMapping("/register")
	@Operation(summary="회원 등록", description="신규 회원을 등록합니다.")
	public ResponseEntity<String>register(@RequestBody ReqRegisterDTO request) {
		memberService.register(request);

		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}


	@PostMapping("/login")
	@Operation(summary="로그인", description="사용자 로그인 기능을 담당합니다.")
	public ResponseEntity<String> login(@RequestBody ReqLoginDTO request, HttpSession session) {
		System.out.println(request);
	    ResLoginDTO loginUser = memberService.login(request);

	    if (loginUser == null) {
	        return new ResponseEntity<String>("조회 실패", HttpStatus.NOT_FOUND);
	    }

	    session.setAttribute("LOGIN_USER", loginUser);
	    return new ResponseEntity<String>("OK", HttpStatus.OK);
	}


	@GetMapping("/logout")
	@Operation(summary="로그아웃", description="로그아웃 기능을 담당합니다.")
	public ResponseEntity<String> logout(HttpSession session) {


		session.invalidate(); // 세션 무효화

		return new ResponseEntity<String>("OK", HttpStatus.OK);

	}
	
	@GetMapping("/check")
	@Operation(summary="로그인 확인", description="회원이 로그인 상태인지 확인합니다.")
	public ResponseEntity<ResLoginDTO> loginCheck(HttpSession session) {
		ResLoginDTO loginUser = (ResLoginDTO) session.getAttribute("LOGIN_USER");
		
		if (loginUser == null) {
			return new ResponseEntity<ResLoginDTO>(loginUser, HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<ResLoginDTO>(loginUser, HttpStatus.OK);
	}
}
