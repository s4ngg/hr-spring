package kr.co.hr.itcontact.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import kr.co.hr.itcontact.dto.ItContactRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    private String name;
    private String dept;
    private String contact;
    private String email;
    private String subject;
    private String message;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    
    public static ItContact of(ItContactRequestDto dto) {
        return new ItContact(
                null,
                dto.getName(),
                dto.getDept(),
                dto.getContact(),
                dto.getEmail(),
                dto.getSubject(),
                dto.getMessage(),
                null
        );
    }
}