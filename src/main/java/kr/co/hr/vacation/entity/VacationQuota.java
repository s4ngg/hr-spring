package kr.co.hr.vacation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import kr.co.hr.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class VacationQuota {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quotaId; // PK - @Id 추가!
	
	// 한 직원이 연도별로 여러 할당 가능 → ManyToOne
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    private Integer year;
    private Integer totalDays; 
    private Integer usedDays;  

    @Version
    private Long version;
}
