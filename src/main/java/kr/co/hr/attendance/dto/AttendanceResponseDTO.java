package kr.co.hr.attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import kr.co.hr.attendance.entity.Attendance;
import lombok.Getter;

@Getter
public class AttendanceResponseDTO {

    private Long attendanceId;
    private Long memberId;
    private String memberName;
    private LocalDate workDate;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private Double workHours;
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