package kr.co.hr.findpassword.service;

import kr.co.hr.findpassword.dto.ResetPasswordRequestDto;
import kr.co.hr.findpassword.dto.SendCodeRequestDto;
import kr.co.hr.findpassword.dto.VerifyCodeRequestDto;

public interface FindPasswordService {
    void sendVerificationCode(SendCodeRequestDto dto);
    void verifyCode(VerifyCodeRequestDto dto);
    void resetPassword(ResetPasswordRequestDto dto);
}