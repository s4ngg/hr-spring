package kr.co.hr.login.controller.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.login.dto.LoginRequestDTO;
import kr.co.hr.login.dto.LoginResponseDTO;
import kr.co.hr.login.dto.docs.ApiResponseLogin;

@Tag(name = "Login", description = "로그인 관련 API")
public interface LoginControllerDocs {

    @Operation(summary = "로그인", description = "사용자의 사번 or 이메일 비밀번호를 받아 로그인을 진행하고 성공시 JWT 토큰 반환.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseLogin.class),
                examples = @ExampleObject(value = """
                    {
                        "token": "eyJhbGciOiJIUzI1NiJ9..."
                    }
                """)
            )),
        @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 올바르지 않습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "아이디 또는 비밀번호가 올바르지 않습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto);
}