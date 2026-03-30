package kr.co.studyProject.member.serviceimpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.studyProject.member.dto.ReqLoginDTO;
import kr.co.studyProject.member.dto.ReqRegisterDTO;
import kr.co.studyProject.member.dto.ResLoginDTO;
import kr.co.studyProject.member.entity.Member;
import kr.co.studyProject.member.repository.MemberRepository;
import kr.co.studyProject.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void register(ReqRegisterDTO request) {
        // 1️⃣ 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 2️⃣ Member 엔티티 생성 및 값 세팅
        Member member = new Member();
        member.setUserName(request.getUserName());
        member.setEmail(request.getEmail());
        member.setPassword(encodedPassword);
        member.setNickName(request.getNickName());
        member.setPhoneNumber(request.getPhoneNumber());

        // 3️⃣ DB 저장
        memberRepository.save(member);
    }
    @Override
    public ResLoginDTO login(ReqLoginDTO request) {
    	
//    	1. 해당 아이디를 가진 유저가 있는지 조회
//    	 - 존재한다면 해당 정보(SELECT된 결과)를 엔티티에 저장
    	Member member = memberRepository.findByUserId(request.getUserId());
//    	2. 존재하지 않으면 null 반환
    	if(member == null) {
    		return null;
    	}
//    	3. 사용자가 입력한 비밀번호가 암호화된 비밀번호와 일치하는지 검증
//    	 - 일치하면 응답 객체 반환
//    	 - 일치하지 않으면 null 반환
    	if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
    		return null;
    	}
    	
    	ResLoginDTO response = new ResLoginDTO();
    	response.setId(member.getId());
    	response.setUserId(member.getUserId());
    	response.setUserName(member.getUserName());
    	response.setCreatedAt(member.getCreatedAt());
    	response.setUpdatedAt(member.getUpdatedAt());
    	
    	return response;
    }
}