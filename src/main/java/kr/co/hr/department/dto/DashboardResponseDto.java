package kr.co.hr.department.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDto {
    
    private Long totalMembers;        // 전체 직원 수
    private Long activeMembers;       // 활성 직원 수
    private Long inactiveMembers;     // 비활성 직원 수
    private Long totalDepartments;    // 전체 부서 수
    private Long pendingVacations;    // 승인 대기 휴가 수
    private Long todayAttendance;     // 오늘 출근 수
}