package kr.co.hr.vacation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
	
    @OneToOne 
    @JoinColumn(name = "member_id")
    private Member member;
    
    private Integer quotaId;
    private Integer year;
    private Integer totalDays; 
    private Integer usedDays;  

}
