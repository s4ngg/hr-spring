package kr.co.hr.itcontact.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.global.response.docs.ApiResponseVoid;
import kr.co.hr.itcontact.dto.ItContactRequestDto;
import kr.co.hr.itcontact.dto.ItContactResponseDto;
import kr.co.hr.itcontact.dto.docs.ApiResponseItContactList;

@Tag(name = "ItContact", description = "IT 문의 API")
public interface ItContactControllerDocs {

    @Operation(summary = "IT 문의 접수", description = "IT 지원팀에 문의를 접수합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "문의 접수 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVoid.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "문의가 접수되었습니다.",
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
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "메일 전송에 실패했습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "메일 전송에 실패했습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<Void>> createContact(@RequestBody @Valid ItContactRequestDto dto);

    @Operation(summary = "IT 문의 목록 조회", description = "접수된 IT 문의 목록을 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseItContactList.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "문의 목록 조회 성공",
                        "data": [
                            {
                                "contactId": 1,
                                "name": "홍길동",
                                "dept": "개발팀",
                                "contact": "010-0000-0000",
                                "email": "example@nexuspro.com",
                                "subject": "시스템 오류 문의",
                                "message": "상세 문의 내용",
                                "createdAt": "2026-01-01T00:00:00"
                            }
                        ]
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<List<ItContactResponseDto>>> getContactList();
}