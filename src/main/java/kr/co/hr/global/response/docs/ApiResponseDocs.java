package kr.co.hr.global.response.docs;

import java.util.List;

import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.dashboard.dto.DashboardResponseDto;
import kr.co.hr.department.dto.DepartmentResponseDto;
import kr.co.hr.itcontact.dto.ItContactResponseDto;
import kr.co.hr.login.dto.LoginResponseDTO;
import kr.co.hr.member.dto.MemberResponseDTO;
import kr.co.hr.vacation.dto.VacationResponseDTO;
import kr.co.hr.global.response.ApiResponse;

public class ApiResponseDocs {

    // Dashboard
    public static class Dashboard extends ApiResponse<DashboardResponseDto> {}

    // Attendance
    public static class Attendance extends ApiResponse<AttendanceResponseDTO> {}
    public static class AttendanceList extends ApiResponse<List<AttendanceResponseDTO>> {}

    // Department
    public static class DepartmentList extends ApiResponse<List<DepartmentResponseDto>> {}

    // ItContact
    public static class ItContactList extends ApiResponse<List<ItContactResponseDto>> {}

    // Login
    public static class Login extends ApiResponse<LoginResponseDTO> {}

    // Member
    public static class Member extends ApiResponse<MemberResponseDTO> {}
    public static class MemberList extends ApiResponse<List<MemberResponseDTO>> {}

    // Vacation
    public static class VacationList extends ApiResponse<List<VacationResponseDTO>> {}

    // Void (공통)
    public static class Void extends ApiResponse<java.lang.Void> {}
}