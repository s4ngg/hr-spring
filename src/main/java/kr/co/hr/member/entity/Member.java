package kr.co.hr.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import kr.co.hr.department.entity.Department;
import kr.co.hr.member.dto.MemberRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	
	private String employeeNo;
	private String name;
	private String email;
	private String password;
	private String role;
	private String status;
	private String employType;
	private String profileImage;

	private LocalDate hireDate;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	public void update(MemberRequestDTO dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.role = dto.getRole();
        this.status = dto.getStatus();
        this.employType = dto.getEmployType();
        this.hireDate = dto.getHireDate();
        this.profileImage = dto.getProfileImage();
    }
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
	public void updateDepartment(Department department) {
	    this.department = department;
	}
}

