package kr.co.hr.dashboard.service;

import kr.co.hr.dashboard.dto.DashboardResponseDto;

public interface DashboardService {
    DashboardResponseDto getDashboard(Long memberId);
}