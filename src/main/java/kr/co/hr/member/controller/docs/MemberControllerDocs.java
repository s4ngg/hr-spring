package kr.co.hr.member.controller.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;
import kr.co.hr.member.dto.docs.ApiResponseMember;
import kr.co.hr.member.dto.docs.ApiResponseMemberList;

@Tag(name = "Member", description = "직원 관련 API")
public interface MemberControllerDocs {

    @Operation(summary = "전체 직원 조회", description = "등록된 모든 직원 목록을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseMemberList.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "조회 성공",
                        "data": [
                            {
                                "memberId": 1,
                                "name": "홍길동",
                                "employeeNo": "EMP001",
                                "email": "hong@company.com",
                                "role": "USER",
                                "employType": "정규직",
                                "hireDate": "2024-01-01",
                                "status": "재직"
                            }
                        ]
                    }
                """)
            ))
    })
    ResponseEntity<kr.co.hr.global.response.ApiResponse<Page<MemberResponseDTO>>> getAllMembers(
            @PageableDefault(size = 10, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String name);

    @Operation(summary = "단일 직원 조회", description = "특정 직원의 정보를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseMember.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "조회 성공",
                        "data": {
                            "memberId": 1,
                            "name": "홍길동",
                            "employeeNo": "EMP001",
                            "email": "hong@company.com",
                            "role": "USER",
                            "employType": "정규직",
                            "hireDate": "2024-01-01",
                            "status": "재직"
                        }
                    }
                """)
            )),
        @ApiResponse(responseCode = "404", description = "해당 직원이 없습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "해당 직원이 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<kr.co.hr.global.response.ApiResponse<MemberResponseDTO>> getMember(@PathVariable Long memberId);

    @Operation(summary = "직원 등록", description = "새로운 직원을 등록합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "등록 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseMember.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "등록 성공",
                        "data": {
                            "memberId": 1,
                            "name": "홍길동",
                            "employeeNo": "EMP001",
                            "email": "hong@company.com",
                            "role": "USER",
                            "employType": "정규직",
                            "hireDate": "2024-01-01",
                            "status": "재직"
                        }
                    }
                """)
            )),
        @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "잘못된 요청입니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<kr.co.hr.global.response.ApiResponse<MemberResponseDTO>> createMember(@RequestBody MemberRequestDTO requestDTO);

    @Operation(summary = "직원 정보 수정", description = "특정 직원의 정보를 수정합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "수정 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseMember.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "수정 성공",
                        "data": {
                            "memberId": 1,
                            "name": "홍길동",
                            "employeeNo": "EMP001",
                            "email": "hong@company.com",
                            "role": "USER",
                            "employType": "정규직",
                            "hireDate": "2024-01-01",
                            "status": "재직"
                        }
                    }
                """)
            )),
        @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "잘못된 요청입니다.",
                        "data": null
                    }
                """)
            )),
        @ApiResponse(responseCode = "404", description = "해당 직원이 없습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "해당 직원이 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<kr.co.hr.global.response.ApiResponse<MemberResponseDTO>> updateMember(@PathVariable Long memberId, @RequestBody MemberRequestDTO requestDTO);

    @Operation(summary = "직원 삭제", description = "특정 직원을 삭제합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "해당 직원이 없습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "해당 직원이 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<kr.co.hr.global.response.ApiResponse<Void>> deleteMember(@PathVariable Long memberId);
}