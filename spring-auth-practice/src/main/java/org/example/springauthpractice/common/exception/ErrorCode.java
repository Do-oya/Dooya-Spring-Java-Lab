package org.example.springauthpractice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // User Error
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 유저입니다."),

    // Login Error
    USER_NOT_MATCH_LOGIN_INFO(HttpStatus.BAD_REQUEST, "로그인 정보에 해당하는 유저가 존재하지 않습니다."),

    // Role Error
    ROLE_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않은 Role 입니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한 없음"),

    // Invalidation Error
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "요청한 JSON 형식이 올바르지 않습니다."),
    INVALID_SESSION_CAPACITY(HttpStatus.BAD_REQUEST, "세션 정원 값이 잘못되었습니다."),
    INVALID_SESSION_TIME(HttpStatus.BAD_REQUEST, "세션 시간이 잘못되었습니다. 과거 시간일 수 없습니다."),
    INVALID_SESSION_NAME(HttpStatus.BAD_REQUEST, "세션 이름을 입력해야 합니다."),
    INVALID_SESSION_LOCATION(HttpStatus.BAD_REQUEST, "세션 장소를 입력해야 합니다."),
    INVALID_RESERVATION_STATUS(HttpStatus.BAD_REQUEST, "알 수 없거나 처리할 수 없는 예약 상태입니다."),

    // JWT Error
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED,"토큰의 서명이 유효하지 않습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED,"잘못된 형식의 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,"만료된 토큰입니다."),
    UNKNOWN_TOKEN_ERROR(HttpStatus.BAD_REQUEST,"토큰의 값이 존재하지 않습니다."),
    MISSING_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

}
