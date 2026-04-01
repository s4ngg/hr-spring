package kr.co.hr.vacation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "휴가 관리자 처리 요청 DTO")
public class VacationAdminRequestDTO {
	@Schema(description = "휴가 승인 상태 (APPROVED/REJECTED)", example = "APPROVED", requiredMode = Schema.RequiredMode.REQUIRED)
	private String status;

	@Schema(description = "반려 사유 (반려 시 필수)", example = "일정 조율 필요")
	private String adminComment;
}
