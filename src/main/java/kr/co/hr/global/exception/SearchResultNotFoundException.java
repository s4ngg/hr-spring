package kr.co.hr.global.exception;

import org.springframework.http.HttpStatus;

public class SearchResultNotFoundException extends BusinessException {
    public SearchResultNotFoundException() {
        super("검색 결과가 없습니다.", HttpStatus.NOT_FOUND);
    }
}