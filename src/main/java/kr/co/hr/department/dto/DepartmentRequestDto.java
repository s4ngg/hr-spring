package kr.co.hr.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "부서 요청 DTO")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDto {

	@Schema(description = "부서 코드", example = "DEV")
    @NotBlank(message = "부서 코드는 필수입니다.")
    @Size(max = 10, message = "부서 코드는 10자 이하여야 합니다.")
    private String deptCode;

	@Schema(description = "부서명", example = "개발팀")
    @NotBlank(message = "부서명은 필수입니다.")
    @Size(max = 50, message = "부서명은 50자 이하여야 합니다.")
    private String deptName;

	@Schema(description = "부서장 ID (선택)", example = "1")
    private Long managerId; // 선택값이라 검사 안 함
}