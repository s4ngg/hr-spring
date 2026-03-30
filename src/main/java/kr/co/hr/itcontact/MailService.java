package kr.co.hr.itcontact;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import kr.co.hr.itcontact.dto.ItContactRequestDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Async
    public void sendItContactMail(ItContactRequestDto dto) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(mailProperties.getEmail());
            mail.setSubject("[IT 문의] " + dto.getSubject());
            mail.setText(buildMailContent(dto));
            mailSender.send(mail);
        } catch (Exception e) {
            throw new RuntimeException("메일 전송에 실패했습니다. 잠시 후 다시 시도해주세요.");
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
}