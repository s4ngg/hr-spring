package kr.co.hr.dashboard.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.attendance.entity.Attendance;
import kr.co.hr.attendance.repository.AttendanceRepository;
import kr.co.hr.dashboard.dto.DashboardResponseDto;
import kr.co.hr.dashboard.service.DashboardService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import kr.co.hr.vacation.entity.VacationQuota;
import kr.co.hr.vacation.enums.VacationStatus;
import kr.co.hr.vacation.repository.VacationQuotaRepository;
import kr.co.hr.vacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;
    private final VacationQuotaRepository vacationQuotaRepository;
    private final VacationRepository vacationRepository;

    @Transactional(readOnly = true)
    @Override
    public DashboardResponseDto getDashboard(Long memberId) {

        // 1. 직원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 직원이 없습니다."));

        // 2. 오늘 근태 조회
        LocalDate today = LocalDate.now();
        Attendance todayAttendance = attendanceRepository
                .findByMember_MemberIdAndWorkDate(memberId, today)
                .orElse(null);

        // 3. 휴가 할당 조회
        int currentYear = today.getYear();
        VacationQuota quota = vacationQuotaRepository
                .findByMember_MemberIdAndYear(memberId, currentYear)
                .orElse(null);

        // 4. 이번 달 출근 일수
        YearMonth currentMonth = YearMonth.now();
        LocalDate firstDay = currentMonth.atDay(1);
        LocalDate lastDay = currentMonth.atEndOfMonth();
        int monthlyWorkDays = attendanceRepository
                .countByMember_MemberIdAndWorkDateBetween(memberId, firstDay, lastDay);

     // 5. 대기 중인 휴가 승인 건수
        int pendingCount = (int) vacationRepository
                .findByStatusWithMember(VacationStatus.PENDING)
                .stream()
                .filter(v -> v.getMember().getMemberId().equals(memberId))
                .count();

        // 6. 최근 근태 이력
        List<Attendance> recentList = attendanceRepository
                .findTop5ByMember_MemberIdOrderByWorkDateDesc(memberId);

        
        int monthlyTotalDays = calculateWeekdays(firstDay, lastDay);
        
        return DashboardResponseDto.of(
                member,
                todayAttendance,
                quota,
                monthlyWorkDays,
                monthlyTotalDays,
                pendingCount,
                recentList
        );
    }
    
    // 요번달에 출근 일수를 총으로 봐야하니
    private int calculateWeekdays(LocalDate firstDay, LocalDate lastDay) {
        int weekdays = 0;
        LocalDate date = firstDay;
        while (!date.isAfter(lastDay)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY
                    && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                weekdays++;
            }
            date = date.plusDays(1);
        }
        return weekdays;
    }
    
}