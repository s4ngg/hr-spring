package kr.co.studyProject.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.studyProject.member.dto.ReqRegisterDTO;
import kr.co.studyProject.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

//    // 🔹 테스트용
//    @GetMapping("/ping")
//    @ResponseBody
//    public String ping() {
//        return "ok";
//    }
   
    // 회원가입 페이지 이동
    @GetMapping("/signup/form")
    public String signup() {
    	return "pages/member/signup";
    }
    
    @PostMapping("/signup")
    public String signup(ReqRegisterDTO dto) {
    	memberService.register(dto);

    	return "redirect:/member/login/form";
    }
}