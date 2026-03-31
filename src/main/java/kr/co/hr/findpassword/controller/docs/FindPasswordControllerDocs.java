package kr.co.hr.findpassword.controller.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.findpassword.dto.ResetPasswordRequestDto;
import kr.co.hr.findpassword.dto.SendCodeRequestDto;
import kr.co.hr.findpassword.dto.VerifyCodeRequestDto;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.global.response.docs.ApiResponseVoid;

@Tag(name = "FindPassword", description = "비밀번호 찾기 API")
public interface FindPasswordControllerDocs {

    @Operation(summary = "인증번호 전송", description = "입력한 이메일로 인증번호를 전송합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인증번호 전송 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVoid.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "인증번호가 전송되었습니다.",
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
    ResponseEntity<ApiResponse<Void>> sendCode(@RequestBody @Valid SendCodeRequestDto dto);

    @Operation(summary = "인증번호 검증", description = "입력한 인증번호를 검증합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인증번호 검증 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVoid.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "인증번호가 확인되었습니다.",
                        "data": null
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "인증번호가 올바르지 않거나 만료되었습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "인증번호가 올바르지 않거나 만료되었습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<Void>> verifyCode(@RequestBody @Valid VerifyCodeRequestDto dto);

    @Operation(summary = "비밀번호 재설정", description = "새로운 비밀번호로 변경합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "비밀번호 변경 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVoid.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "비밀번호가 변경되었습니다.",
                        "data": null
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "인증이 완료되지 않았습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "인증이 완료되지 않았습니다.",
                        "data": null
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 직원이 없습니다.",
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
    ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody @Valid ResetPasswordRequestDto dto);
}