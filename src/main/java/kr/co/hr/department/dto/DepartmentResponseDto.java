package kr.co.hr.department.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto {

    private Long departmentId;
    private String deptCode;
    private String deptName;
    private String managerName;  // 부서장 이름
    private Long memberCount;    // 부서 인원 수
    private LocalDateTime createdAt;
}