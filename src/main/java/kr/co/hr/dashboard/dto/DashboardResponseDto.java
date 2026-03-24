package kr.co.hr.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Schema(description = "대시보드 응답 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDto {

    @Schema(description = "직원 이름", example = "홍길동")
    private String memberName;

    @Schema(description = "오늘 출근 시간")
    private LocalTime todayCheckIn;

    @Schema(description = "오늘 퇴근 시간")
    private LocalTime todayCheckOut;

    @Schema(description = "오늘 근태 상태", example = "NORMAL")
    private String todayStatus;

    @Schema(description = "잔여 휴가 일수", example = "12")
    private Integer remainVacationDays;

    @Schema(description = "총 휴가 일수", example = "15")
    private Integer totalVacationDays;

    @Schema(description = "이번 달 출근 일수", example = "18")
    private Integer monthlyWorkDays;

    @Schema(description = "이번 달 총 근무일", example = "22")
    private Integer monthlyTotalDays;

    @Schema(description = "대기 중인 휴가 승인 건수", example = "3")
    private Integer pendingVacationCount;

    @Schema(description = "최근 근태 이력")
    private List<RecentAttendance> recentAttendances;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentAttendance {
        private LocalDate workDate;
        private LocalTime checkIn;
        private LocalTime checkOut;
        private Double workHours;
        private String status;
    }
}