package kr.co.hr.attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequestDTO {

	private Long memberId;
	private LocalDate workDate;
	private LocalTime checkIn;
	private LocalTime checkOut;
	private Double workHours;
	private String status;
}
