package kr.co.hr.attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.hr.attendance.entity.Attendance;
import lombok.Getter;

@Getter
@Schema(description = "근태 응답 DTO")
public class AttendanceResponseDTO {

	@Schema(description = "근태 ID", example = "1")
    private Long attendanceId;
	
	@Schema(description = "직원 ID", example = "1")
    private Long memberId;
	
	@Schema(description = "직원 이름", example = "홍길동")
    private String memberName;
	
	@Schema(description= "근무 날짜", example = "2024-01-01")
    private LocalDate workDate;
	
	@Schema(description = "출근 시간", example = "09:00:00")
    private LocalTime checkIn;
	
	@Schema(description = "퇴근 시간", example = "18:00:00")
    private LocalTime checkOut;
	
	@Schema(description = "근무 시간", example = "8.0")
    private Double workHours;
	
	@Schema(description = "근태 상태 (정상/지각/조퇴/결근", example = "정상")
    private String status;

    public AttendanceResponseDTO(Attendance attendance) {
        this.attendanceId = attendance.getAttendanceId();
        this.memberId = attendance.getMember().getMemberId();
        this.memberName = attendance.getMember().getName();
        this.workDate = attendance.getWorkDate();
        this.checkIn = attendance.getCheckIn();
        this.checkOut = attendance.getCheckOut();
        this.workHours = attendance.getWorkHours();
        this.status = attendance.getStatus();
    }
}