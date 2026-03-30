//package kr.co.study.board.controller;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import jakarta.servlet.http.HttpSession;
//import kr.co.study.board.dto.ReqBoardDTO;
//import kr.co.study.board.service.BoardService;
//import kr.co.study.member.dto.ResLoginDTO;
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequestMapping("/board/notice")
//@RequiredArgsConstructor
//public class BoardController {
//
//    private final BoardService boardService;
//
//    @GetMapping
//    public String noticeList() {
//        return "pages/board/notice";
//    }
//
//    @GetMapping("/create/form")
//    public String createForm(HttpSession session) {
//
//        ResLoginDTO loginUser =
//                (ResLoginDTO) session.getAttribute("LOGIN_USER");
//
//        if (loginUser == null) {
//            return "redirect:/member/login/form";
//        }
//
//        return "pages/board/notice-write";
//    }
//
//    @PostMapping("/create")
//    public String create(ReqBoardDTO request, HttpSession session) {
//
//        ResLoginDTO loginUser =
//                (ResLoginDTO) session.getAttribute("LOGIN_USER");
//
//        if (loginUser == null) {
//            return "redirect:/member/login/form";
//        }
//
//        boardService.write(request, loginUser.getId());
//
//        return "redirect:/board/notice";
//    }
//}