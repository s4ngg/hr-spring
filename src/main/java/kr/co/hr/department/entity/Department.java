package kr.co.hr.department.entity;

import kr.co.hr.member.entity.Member;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Member manager;

    private String deptCode;
    private String deptName;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    
    public void update(String deptCode, String deptName, Member manager) {
    	if (deptCode != null) this.deptCode = deptCode;
    	if (deptName != null) this.deptName = deptName;
    	this.manager = manager; // 널이여도 부서장 없음으로 처리
    }
}