package kr.co.hr.department.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.department.dto.DepartmentRequestDto;
import kr.co.hr.department.dto.DepartmentResponseDto;
import kr.co.hr.department.dto.docs.ApiResponseDepartmentList;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.global.response.docs.ApiResponseVoid;

@Tag(name = "Department", description = "부서 관리 API")
public interface DepartmentControllerDocs {

    @Operation(summary = "부서 목록 조회", description = "전체 부서 목록을 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseDepartmentList.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "부서 목록 조회 성공",
                        "data": [
                            {
                                "departmentId": 1,
                                "deptCode": "DEV",
                                "deptName": "개발팀",
                                "managerName": "홍길동",
                                "memberCount": 10,
                                "createdAt": "2026-01-01T00:00:00"
                            }
                        ]
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> getDepartmentList();

    @Operation(summary = "부서 검색", description = "부서명으로 부서를 검색합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "검색 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseDepartmentList.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "부서 검색 성공",
                        "data": [
                            {
                                "departmentId": 1,
                                "deptCode": "DEV",
                                "deptName": "개발팀",
                                "managerName": "홍길동",
                                "memberCount": 10,
                                "createdAt": "2026-01-01T00:00:00"
                            }
                        ]
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "검색 결과 없음",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "검색 결과가 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> searchDepartment(@RequestParam String deptName);

    @Operation(summary = "부서 생성", description = "새로운 부서를 생성합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVoid.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "부서 생성 성공",
                        "data": null
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
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
    ResponseEntity<ApiResponse<Void>> createDepartment(@RequestBody @Valid DepartmentRequestDto dto);

    @Operation(summary = "부서 수정", description = "부서 정보를 수정합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVoid.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "부서 수정 성공",
                        "data": null
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "잘못된 요청입니다.",
                        "data": null
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 부서가 없습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "해당 부서가 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<Void>> updateDepartment(@PathVariable Long departmentId, @RequestBody @Valid DepartmentRequestDto dto);

    @Operation(summary = "부서 삭제", description = "부서를 삭제합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVoid.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "부서 삭제 성공",
                        "data": null
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 부서가 없습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "해당 부서가 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable Long departmentId);
}