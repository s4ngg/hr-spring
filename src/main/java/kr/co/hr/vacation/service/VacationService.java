package kr.co.hr.vacation.service;

import java.util.List;

import kr.co.hr.vacation.dto.VacationAdminRequestDTO;
import kr.co.hr.vacation.dto.VacationRequestDTO;
import kr.co.hr.vacation.dto.VacationResponseDTO;

public interface VacationService {
	
	// 휴가 신청
	void requestVacation(VacationRequestDTO dto);
	
	// 휴가 신청 내역 조회
	List<VacationResponseDTO> getMyVacationHistory(Long memberId);
	
	// 관리자용 승인 대기 목록 조회 
	List<VacationResponseDTO> getPendingVacations();
	
	// [추가] 관리자용 승인/반려 처리
	void updateVacationStatus(Long vacationId, VacationAdminRequestDTO dto);
}
