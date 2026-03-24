//package kr.co.hr.dashboard.service.impl;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.YearMonth;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import kr.co.hr.attendance.entity.Attendance;
//import kr.co.hr.attendance.repository.AttendanceRepository;
//import kr.co.hr.dashboard.dto.DashboardResponseDto;
//import kr.co.hr.dashboard.service.DashboardService;
//import kr.co.hr.member.entity.Member;
//import kr.co.hr.member.repository.MemberRepository;
//import kr.co.hr.vacation.entity.VacationQuota;
//import kr.co.hr.vacation.repository.VacationQuotaRepository;
//import kr.co.hr.vacation.repository.VacationRepository;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class DashboardServiceImpl implements DashboardService {
//
//    private final MemberRepository memberRepository;
//    private final AttendanceRepository attendanceRepository;
//    private final VacationQuotaRepository vacationQuotaRepository;
//    private final VacationRepository vacationRepository;
//
//    @Transactional(readOnly = true)
//    @Override
//    public DashboardResponseDto getDashboard(Long memberId) {
//
//        // 1. 직원 조회
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("해당 직원이 없습니다."));
//
//        // 2. 오늘 근태 조회
//        LocalDate today = LocalDate.now();
//        Attendance todayAttendance = attendanceRepository
//                .findByMemberMemberIdAndWorkDate(memberId, today)
//                .orElse(null);
//
//        LocalTime checkIn = todayAttendance != null ? todayAttendance.getCheckIn() : null;
//        LocalTime checkOut = todayAttendance != null ? todayAttendance.getCheckOut() : null;
//        String todayStatus = todayAttendance != null ? todayAttendance.getStatus() : "미출근";
//
//        // 3. 휴가 할당 조회 (올해)
//        int currentYear = today.getYear();
//        VacationQuota quota = vacationQuotaRepository
//                .findByMemberMemberIdAndYear(memberId, currentYear)
//                .orElse(null);
//
//        Integer totalDays = quota != null ? quota.getTotalDays() : 0;
//        Integer usedDays = quota != null ? quota.getUsedDays() : 0;
//        Integer remainDays = totalDays - usedDays;
//
//        // 4. 이번 달 출근 일수
//        YearMonth currentMonth = YearMonth.now();
//        LocalDate firstDay = currentMonth.atDay(1);
//        LocalDate lastDay = currentMonth.atEndOfMonth();
//
//        int monthlyWorkDays = attendanceRepository
//                .countByMemberMemberIdAndWorkDateBetween(memberId, firstDay, lastDay);
//
//        // 이번 달 평일 수 (간단하게 처리)
//        int monthlyTotalDays = 22;
//
//        // 5. 대기 중인 휴가 승인 건수
//        int pendingCount = vacationRepository
//                .countByMemberMemberIdAndStatus(memberId, "PENDING");
//
//        // 6. 최근 근태 이력 (최근 5개)
//        List<Attendance> recentList = attendanceRepository
//                .findTop5ByMemberMemberIdOrderByWorkDateDesc(memberId);
//
//        List<DashboardResponseDto.RecentAttendance> recentAttendances = recentList.stream()
//                .map(a -> DashboardResponseDto.RecentAttendance.builder()
//                        .workDate(a.getWorkDate())
//                        .checkIn(a.getCheckIn())
//                        .checkOut(a.getCheckOut())
//                        .workHours(a.getWorkHours())
//                        .status(a.getStatus())
//                        .build())
//                .collect(Collectors.toList());
//
//        return DashboardResponseDto.builder()
//                .memberName(member.getName())
//                .todayCheckIn(checkIn)
//                .todayCheckOut(checkOut)
//                .todayStatus(todayStatus)
//                .remainVacationDays(remainDays)
//                .totalVacationDays(totalDays)
//                .monthlyWorkDays(monthlyWorkDays)
//                .monthlyTotalDays(monthlyTotalDays)
//                .pendingVacationCount(pendingCount)
//                .recentAttendances(recentAttendances)
//                .build();
//    }
//}