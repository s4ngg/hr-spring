package kr.co.hr.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDto {

    @NotBlank(message = "부서 코드는 필수입니다.")
    @Size(max = 10, message = "부서 코드는 10자 이하여야 합니다.")
    private String deptCode;

    @NotBlank(message = "부서명은 필수입니다.")
    @Size(max = 50, message = "부서명은 50자 이하여야 합니다.")
    private String deptName;

    private Long managerId; // 선택값이라 검사 안 함
}