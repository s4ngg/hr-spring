package kr.co.study.board.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.co.study.board.dto.ReqBoardDTO;
import kr.co.study.board.dto.ResBoardDTO;
import kr.co.study.board.service.BoardService;
import kr.co.study.member.dto.ResLoginDTO;
import lombok.RequiredArgsConstructor;

@Controller // 이거 안붙이면 bean으로 생성안함
@RequestMapping("/board/notice")
@RequiredArgsConstructor
public class NoticeController { // 공지사항 컨트롤러

	private final BoardService boardService;

	/**
	 * 공지사항 페이지로 이동하는 메서드
	 * 
	 * @return notice.html
	 * 
	 */
	@GetMapping
	public String noticeList(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {

		/**
		 * 게시글 리스트 조회기능 - boardType으로 게시판 종류를 확인 후 조회 - 조회된 Board 엔티티를 Response DTO 타입으로
		 * 변환
		 */

//      1. 공지사항 목록 조회
		Page<ResBoardDTO> list = boardService.getBoardList(page - 1);
//      페이징 버튼 블록 계산
//       - 현재 페이지가 3페이지다 -> 1~5페이지 보이게
//       - 현재 페이지가 7페이지다 -> 6~10페이지 보이게
		int currentPage = list.getNumber() + 1; // 현재 페이지(JPA는 0부터 시작하므로 +1)
		int totalPages = list.getTotalPages(); // 전체 페이지

		int blockSize = 5; // 5개의 버튼씩 보이게

		int startPage = ((currentPage - 1) / blockSize) * blockSize + 1;

		int endPage = startPage + blockSize - 1;

		if (endPage > totalPages) {
			endPage = totalPages;

		}

//      2. 모델에 담아 타임리프에 전달
		model.addAttribute("list", list);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

//      3. 타임리프로 이동 
		return "pages/board/notice";
	}

	/**
	 * 공지사항의 게시글 상세보기 페이지로 이동될 때 사용하는 메서드 입니다. - 쿼리스트링으로 전달 받은 id (게시글의 PK)로 조회합니다.
	 * - 조회된 게시글의 정보를 모델에 담아 타임리프에서 사용합니다
	 * 
	 * @param id    게시글 PK
	 * @param model
	 * @return
	 */
	@GetMapping("/detail")
	public String detail(@RequestParam(name = "id") Long id, Model model) {
		ResBoardDTO response = boardService.getBoardDetail(id);
		model.addAttribute("notice", response);
		return "pages/board/notice-detail";
	}

	@GetMapping("/create/form")
	public String createForm() {
		return "pages/board/notice-write";
	}

	@PostMapping("/create")
	public String create(ReqBoardDTO request, HttpSession session,
						@RequestParam(value = "files", required = false) List<MultipartFile> files) {
//         System.out.println(request);  // request == request.toString
//         1. 로그인한 사용자 정보 세션에서 꺼내기
		ResLoginDTO loginUser = (ResLoginDTO) session.getAttribute("LOGIN_USER");

//         2. 로그인한 사용자가 아니라면 로그인 페이지로 이동
		if (loginUser == null) {
			return "redirect:/member/login/form";
		}

//         3. 게시글 저장
		boardService.write(request, files, loginUser.getId());

//         4. 목록으로 이동
		return "redirect:/board/notice";
	}

	/**
	 * 공지사항 수정 페이지로 이동하는 메서드입니다.
	 */

	@GetMapping("/edit/form")
	public String editForm(@RequestParam(name = "id") Long id, Model model) {
		ResBoardDTO response = boardService.getBoardDetailEdit(id);
		model.addAttribute("notice", response);

		return "pages/board/notice-edit";

	}

	@PostMapping("/edit")
	public String edit(ReqBoardDTO request, HttpSession session,
						@RequestParam(value = "files", required = false) List<MultipartFile> files) {
//    	  1. 로그인한 사용자 조회
		ResLoginDTO loginUser = (ResLoginDTO) session.getAttribute("LOGIN_USER");

//    	  2. 로그인하지 않은 사용자는 수정 불가
		if (loginUser == null) {
			return "redirect:/member/login/form";
		}
//    	  3. 게시글 수정 진행
		boardService.edit(request, files, loginUser.getId());

		return "redirect:/board/notice/detail?id=" + request.getId();
	}

	@GetMapping("/delete")
	public String delete(@RequestParam(name = "id") Long id, HttpSession session) {
//    	 1. 로그인 사용자 정보 조회
		ResLoginDTO loginUser = (ResLoginDTO) session.getAttribute("LOGIN_USER");
//    	 2. 비로그인 상태면 삭제 불가
		if (loginUser == null) {
			return "redirect:/member/login/form";
		}
//    	  3. 삭제 실행
		boardService.delete(id, loginUser.getId());

		return "redirect:/board/notice";
	}

}
