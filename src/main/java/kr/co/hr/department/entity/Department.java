package kr.co.hr.department.entity;

<<<<<<< Updated upstream
import java.time.LocalDateTime;

=======
import kr.co.hr.member.entity.Member;
import java.time.LocalDateTime;
>>>>>>> Stashed changes
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
<<<<<<< Updated upstream
=======
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
>>>>>>> Stashed changes
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

<<<<<<< Updated upstream
    private String deptCode;
    private String deptName;
    private Long managerId;
=======
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Member manager;
    
    private String deptCode;
    private String deptName;
    
>>>>>>> Stashed changes

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}