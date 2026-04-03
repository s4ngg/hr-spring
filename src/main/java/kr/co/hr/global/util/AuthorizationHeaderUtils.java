package kr.co.hr.global.util;

public final class AuthorizationHeaderUtils {

 private AuthorizationHeaderUtils() {
 }

 public static String extractBearerToken(String authorizationHeader) {
     if (authorizationHeader == null || authorizationHeader.isBlank()) {
         throw new IllegalArgumentException("Authorization 헤더가 비어 있습니다.");
     }

     if (!authorizationHeader.startsWith("Bearer ")) {
         throw new IllegalArgumentException("Bearer 토큰 형식이 아닙니다.");
     }

     return authorizationHeader.substring(7);
 }
}

