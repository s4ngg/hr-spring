package kr.co.hr.attendance.scheduler;

import java.time.LocalDate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import kr.co.hr.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AttendanceScheduler {

    private final AttendanceService attendanceService;

    @Scheduled(cron = "0 0 0 * * *")
    public void processAbsent() {
        attendanceService.processAbsent(LocalDate.now().minusDays(1));
    }
}