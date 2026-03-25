package kr.co.hr.attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "근태 요청 DTO")
public class AttendanceRequestDTO {

	@Schema(description = "직원 ID", example = "1")
	private Long memberId;
}
