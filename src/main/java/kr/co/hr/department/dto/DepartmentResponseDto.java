package kr.co.hr.department.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.hr.department.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "부서 응답 DTO")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto {

    @Schema(description = "부서 ID", example = "1")
    private Long departmentId;

    @Schema(description = "부서 코드", example = "DEV")
    private String deptCode;

    @Schema(description = "부서명", example = "개발팀")
    private String deptName;

    @Schema(description = "부서장 이름", example = "홍길동")
    private String managerName;

    @Schema(description = "부서 인원 수", example = "10")
    private Long memberCount;

    @Schema(description = "생성일시", example = "2026-01-01T00:00:00")
    private LocalDateTime createdAt;

    public static DepartmentResponseDto from(Department department, Long memberCount) {
        return DepartmentResponseDto.builder()
                .departmentId(department.getDepartmentId())
                .deptCode(department.getDeptCode())
                .deptName(department.getDeptName())
                .managerName(department.getManager() != null ? department.getManager().getName() : null)
                .memberCount(memberCount)
                .createdAt(department.getCreatedAt())
                .build();
    }

    public static List<DepartmentResponseDto> fromList(List<Department> departments, Function<Long, Long> countFn) {
        return departments.stream()
                .map(d -> from(d, countFn.apply(d.getDepartmentId())))
                .collect(Collectors.toList());
    }
}