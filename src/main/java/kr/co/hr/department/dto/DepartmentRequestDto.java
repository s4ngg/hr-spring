package kr.co.hr.department.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDto {

    private String deptCode;
    private String deptName;
    private Long managerId;
}