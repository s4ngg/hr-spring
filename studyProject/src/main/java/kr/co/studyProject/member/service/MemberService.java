package kr.co.studyProject.member.service;

import org.springframework.stereotype.Service;

import kr.co.studyProject.member.dto.ReqLoginDTO;
import kr.co.studyProject.member.dto.ReqRegisterDTO;
import kr.co.studyProject.member.dto.ResLoginDTO;

@Service
public interface MemberService {

	public void register(ReqRegisterDTO request);
	 public ResLoginDTO login(ReqLoginDTO request);
}
