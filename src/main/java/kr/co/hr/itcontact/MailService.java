package kr.co.hr.itcontact;

import java.util.concurrent.CompletableFuture;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import kr.co.hr.global.exception.BusinessException;
import kr.co.hr.global.exception.ErrorCode;
import kr.co.hr.itcontact.dto.ItContactRequestDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Async("mailExecutor")
    public CompletableFuture<Void> sendItContactMail(ItContactRequestDto dto) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(mailProperties.getEmail());
            mail.setSubject("[IT 문의] " + dto.getSubject());
            mail.setText(buildMailContent(dto));
            mailSender.send(mail);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
        	throw new BusinessException(ErrorCode.MAIL_SEND_FAIL);
        }
    }

    private String buildMailContent(ItContactRequestDto dto) {
        return String.format("""
                문의자: %s
                부서: %s
                연락처: %s
                이메일: %s

                내용:
                %s
                """,
                dto.getName(),
                dto.getDept(),
                dto.getContact(),
                dto.getEmail(),
                dto.getMessage()
        );
    }
    public void sendVerificationMail(String email, String code) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(email);
            mail.setSubject("[Architect Ledger HR] 비밀번호 찾기 인증번호");
            mail.setText("인증번호: " + code + "\n\n3분 이내에 입력해주세요.");
            mailSender.send(mail);
        } catch (Exception e) {
        	throw new BusinessException(ErrorCode.MAIL_SEND_FAIL);
        }
    }
}