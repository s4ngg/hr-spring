package kr.co.hr.itcontact;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import kr.co.hr.itcontact.dto.ItContactRequestDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${it.team.email}")
    private String itTeamEmail;

    public void sendItContactMail(ItContactRequestDto dto) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(itTeamEmail);
        mail.setSubject("[IT 문의] " + dto.getSubject());
        mail.setText(
                "문의자: " + dto.getName() + "\n" +
                "부서: " + dto.getDept() + "\n" +
                "연락처: " + dto.getContact() + "\n" +
                "이메일: " + dto.getEmail() + "\n\n" +
                "내용:\n" + dto.getMessage()
        );
        mailSender.send(mail);
    }
}