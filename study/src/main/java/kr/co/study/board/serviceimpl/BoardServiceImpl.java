//package kr.co.study.board.serviceimpl;
//
//import org.springframework.stereotype.Service;
//
//import jakarta.transaction.Transactional;
//import kr.co.study.board.dto.ReqBoardDTO;
//import kr.co.study.board.entity.Board;
//import kr.co.study.board.repository.BoardRepository;
//import kr.co.study.board.service.BoardService;
//import kr.co.study.member.entity.Member;
//import kr.co.study.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor 
//public class BoardServiceImpl implements BoardService {
//
//    private final BoardRepository boardRepository;
//    private final MemberRepository memberRepository;
//
//    @Override
//    @Transactional
//    public void write(ReqBoardDTO request, Long writerId) {
//
//        // 1️⃣ 작성자 조회
//        Member writer = memberRepository.findById(writerId).orElse(null);
//
//        if (writer == null) {
//            throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
//        }
//
//        // 2️⃣ Board 엔티티 생성
//        Board board = new Board();
//        board.setBoardType("NOTICE");
//        board.setCategory(request.getCategory());
//        board.setTitle(request.getTitle());
//        board.setContent(request.getContent());
//        board.setWriter(writer);
//        board.setViewCount(0);
//
//        // 3️⃣ DB 저장 (Board만 저장)
//        boardRepository.save(board);
//    }
//}