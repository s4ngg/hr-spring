package kr.co.hr.vacation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long quotaId;
    
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private Integer year;
    private Integer totalDays;
    private Integer usedDays;
}