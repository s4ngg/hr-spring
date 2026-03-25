package kr.co.hr.attendance.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import kr.co.hr.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attendance")
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

    
 // ✅ 출근용 메서드
    @PrePersist
    public void prePersist() {
        this.workDate = LocalDate.now();
        this.checkIn = LocalTime.now();
        this.status = "출근";
    }
    
 // ✅ 퇴근용 메서드
    public void checkOut() {
        this.checkOut = LocalTime.now();
        this.status = "퇴근";
    }
}