package com.sparta.uglymarket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorMsg {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REQUEST(BAD_REQUEST, "잘못된 요청입니다."),
    DUPLICATE_PHONE_NUMBER(BAD_REQUEST, "전화번호가 이미 존재합니다."),
    INVALID_PASSWORD(BAD_REQUEST, "잘못된 비밀번호입니다."),
    INVALID_TOKEN(BAD_REQUEST, "유효하지 않은 토큰입니다."),
    MISSING_AUTHORIZATION_HEADER(BAD_REQUEST, "Authorization 헤더가 없거나 형식이 올바르지 않습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "인증된 사용자가 아닙니다."),
    NOT_LOGGED_IN(UNAUTHORIZED, "로그인이 되어있지 않습니다."),
    PHONE_NUMBER_NOT_FOUND(UNAUTHORIZED, "존재하지 않는 전화번호입니다."),

    /* 403 FORBIDDEN : 권한 없음 */
    NOT_AN_ADMIN(FORBIDDEN, "관리자가 아닙니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    PRODUCT_NOT_FOUND(NOT_FOUND, "상품을 찾을 수 없습니다."),
    ADMIN_NOT_FOUND(NOT_FOUND, "관리자를 찾을 수 없습니다."), // 추가된 부분

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_USER(CONFLICT,"이미 가입된 사용자입니다."),
    DUPLICATE_EMAIL(CONFLICT,"중복된 이메일입니다."),

    /* 500 INTERNAL SERVER ERROR : 그 외 서버 에러 (컴파일 관련) */
    FAILED_TO_EXECUTE_FILE(INTERNAL_SERVER_ERROR, "파일 실행에 실패했습니다."),
    FAILED_TO_COMPILE_FILE(INTERNAL_SERVER_ERROR, "파일 컴파일에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String details;
}
