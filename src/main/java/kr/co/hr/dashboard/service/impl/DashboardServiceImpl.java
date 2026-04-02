package kr.co.hr.dashboard.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
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

@Service
public class DashboardServiceImpl implements DashboardService {
	public DashboardServiceImpl(
		    MemberRepository memberRepository,
		    AttendanceRepository attendanceRepository,
		    VacationQuotaRepository vacationQuotaRepository,
		    VacationRepository vacationRepository,
		    @Qualifier("dashboardExecutor") Executor dashboardExecutor) {
		    this.memberRepository = memberRepository;
		    this.attendanceRepository = attendanceRepository;
		    this.vacationQuotaRepository = vacationQuotaRepository;
		    this.vacationRepository = vacationRepository;
		    this.dashboardExecutor = dashboardExecutor;
		}
	
    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;
    private final VacationQuotaRepository vacationQuotaRepository;
    private final VacationRepository vacationRepository;
    private final Executor dashboardExecutor;

    @Override
    public DashboardResponseDto getDashboard(Long memberId) {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        YearMonth currentMonth = YearMonth.now();
        LocalDate firstDay = currentMonth.atDay(1);
        LocalDate lastDay = currentMonth.atEndOfMonth();

        // 병렬 실행
        CompletableFuture<String> memberFuture = CompletableFuture.supplyAsync(() ->
            memberRepository.findNameById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND)),
            dashboardExecutor);

        CompletableFuture<Attendance> todayAttFuture = CompletableFuture.supplyAsync(() ->
            attendanceRepository.findByMemberIdAndWorkDate(memberId, today).orElse(null),
            dashboardExecutor);

        CompletableFuture<VacationQuota> quotaFuture = CompletableFuture.supplyAsync(() ->
            vacationQuotaRepository.findByMemberIdAndYear(memberId, currentYear).orElse(null),
            dashboardExecutor);

        CompletableFuture<Integer> monthlyWorkFuture = CompletableFuture.supplyAsync(() ->
            attendanceRepository.countByMemberIdAndWorkDateBetween(memberId, firstDay, lastDay),
            dashboardExecutor);

        CompletableFuture<Integer> pendingFuture = CompletableFuture.supplyAsync(() ->
            vacationRepository.countByStatusAndMemberId(VacationStatus.PENDING, memberId),
            dashboardExecutor);

        CompletableFuture<List<Attendance>> recentFuture = CompletableFuture.supplyAsync(() ->
            attendanceRepository.findTop5ByMemberIdOrderByWorkDateDesc(memberId),
            dashboardExecutor);

        // 모든 작업 완료 대기
        CompletableFuture.allOf(memberFuture, todayAttFuture, quotaFuture,
                monthlyWorkFuture, pendingFuture, recentFuture).join();

        try {
            return DashboardResponseDto.of(
                memberFuture.get(),
                todayAttFuture.get(),
                quotaFuture.get(),
                monthlyWorkFuture.get(),
                calculateWeekdays(firstDay, lastDay),
                pendingFuture.get(),
                recentFuture.get()
            );
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
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