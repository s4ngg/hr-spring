package kr.co.hr.findpassword.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String code;
    private LocalDateTime expiredAt;

    public static VerificationCode of(String email, String code) {
        return new VerificationCode(null, email, code, LocalDateTime.now().plusMinutes(3));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }
}