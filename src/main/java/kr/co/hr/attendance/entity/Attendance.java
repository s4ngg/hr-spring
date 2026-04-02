package kr.co.hr.attendance.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.hr.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attendance", indexes = {
    @Index(name = "idx_attendance_member_id", columnList = "member_id"),
    @Index(name = "idx_attendance_work_date", columnList = "workDate"),
    @Index(name = "idx_attendance_member_work_date", columnList = "member_id, workDate")
})
@Getter @Setter
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;
    
    private LocalDate workDate;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private Double workHours;
    private String status;

    
    // 출근 처리
    public static Attendance checkIn(Member member, LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance();
        attendance.member = member;
        attendance.workDate = date;
        attendance.checkIn = time;
        attendance.status = time.isAfter(LocalTime.of(9, 0))
                ? AttendanceStatus.LATE.name()
                : AttendanceStatus.NORMAL.name();
        return attendance;
    }

    //  결근 처리
    public static Attendance absent(Member member, LocalDate date) {
        Attendance attendance = new Attendance();
        attendance.member = member;
        attendance.workDate = date;
        attendance.status = AttendanceStatus.ABSENT.name();
        return attendance;
    }

    //  퇴근 처리
    public void checkOut() {
        this.checkOut = LocalTime.now();
        this.status = AttendanceStatus.NORMAL.name();
        
       if (this.checkIn != null) {
    	   long minutes = java.time.Duration.between(this.checkIn, this.checkOut).toMinutes();
    	   this.workHours = Math.round((minutes / 60.0) * 10.0) / 10.0; //소수점 1자리
       }
    }
}