package kr.co.hr.vacation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import kr.co.hr.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacationId; // PK

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; 

    private String vacationType; 
    private LocalDate startDate; 
    private LocalDate endDate;   
    private Integer days;        
    private String status;       
    
    @PrePersist
    public void prePersist() {
    this.createdAt = LocalDateTime.now();
    }
    private LocalDateTime createdAt;
	
}
