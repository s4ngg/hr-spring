package kr.co.hr.vacation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import kr.co.hr.member.entity.Member;
import kr.co.hr.vacation.enums.VacationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vacation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacationId; // PK

	// 휴가 신청자
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
 // 휴가 할당 FK 추가
    @ManyToOne
    @JoinColumn(name = "quota_id", nullable = false)
    private VacationQuota vacationQuota;

    private String vacationType;
    private LocalDate startDate;
    private LocalDate endDate;
    // days 삭제
    @Enumerated(EnumType.STRING)
    private VacationStatus status;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}



