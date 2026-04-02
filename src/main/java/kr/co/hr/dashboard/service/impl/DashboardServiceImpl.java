package kr.co.hr.dashboard.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.hr.attendance.entity.Attendance;
import kr.co.hr.attendance.repository.AttendanceRepository;
import kr.co.hr.dashboard.dto.DashboardResponseDto;
import kr.co.hr.dashboard.service.DashboardService;
import kr.co.hr.global.exception.BusinessException;
import kr.co.hr.global.exception.ErrorCode;
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

    @Override
    public DashboardResponseDto getDashboard(Long memberId) {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        YearMonth currentMonth = YearMonth.now();
        LocalDate firstDay = currentMonth.atDay(1);
        LocalDate lastDay = currentMonth.atEndOfMonth();

        String memberName = memberRepository.findNameById(memberId)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Attendance todayAttendance = attendanceRepository
            .findByMemberIdAndWorkDate(memberId, today).orElse(null);

        VacationQuota quota = vacationQuotaRepository
            .findByMemberIdAndYear(memberId, currentYear).orElse(null);

        int monthlyWorkDays = attendanceRepository
            .countByMemberIdAndWorkDateBetween(memberId, firstDay, lastDay);

        int pendingCount = vacationRepository
            .countByStatusAndMemberId(VacationStatus.PENDING, memberId);

        List<Attendance> recentList = attendanceRepository
            .findTop5ByMemberIdOrderByWorkDateDesc(memberId);

        int monthlyTotalDays = calculateWeekdays(firstDay, lastDay);

        return DashboardResponseDto.of(
            memberName,
            todayAttendance,
            quota,
            monthlyWorkDays,
            monthlyTotalDays,
            pendingCount,
            recentList
        );
    }

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