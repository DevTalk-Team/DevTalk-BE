package com.devtalk.member.memberservice.global.success;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum SuccessCode {

    /* 회원가입 */
    CHECK_EMAIL_DUPLICATION_SUCCESS(OK, "0110", "사용 가능한 이메일입니다."),
    CONSULTER_SIGNUP_SUCCESS(CREATED, "0111", "멘티 회원가입 성공"),
    CONSULTANT_SIGNUP_SUCCESS(CREATED, "0112", "전문가 회원가입 성공"),
    /* email service */
    SENDING_AUTH_CODE_SUCCESS(OK, "0113", "인증 번호 보내기 성공"),
    AUTH_CODE_SUCCESS(OK, "0114", "인증 번호 일치"),
    /* auth service */
    LOGIN_SUCCESS(OK, "0115", "로그인 성공"),
    LOGOUT_SUCCESS(OK, "0116", "로그아웃 성공"),
    /* profile 찾기 */
    FIND_EMAIL_SUCCESS(OK, "0117", "이메일 찾기 성공"),
    TEMP_PASSWORD_SUCCESS(OK, "0118", "임시 비밀번호 발급 성공"),
    CHANGE_PASSWORD_SUCCESS(OK, "0119", "비밀번호 재설정 성공"),
    FIND_MEMBER_SUCCESS(OK, "0129", "ID로 멤버 조회 성공"),
    /* 전문가 소개 페이지 */
    CONSULTANT_INFO_SUCCESS(OK, "0120", "전문가 소개 페이지 조회 성공"),
    CONSULTANT_INFO_UPDATE_SUCCESS(OK, "0121", "전문가 소개 페이지 수정 성공"),
    CONSULTANT_INFO_TYPE_SUCCESS(OK, "0122", "전문가 소개 - 상담 유형 조회 성공"),
    CONSULTANT_INFO_TYPE_UPDATE_SUCCESS(OK, "0123", "전문가 소개 - 상담 유형 수정 성공"),
    CONSULTANT_INFO_CATEGORY_SUCCESS(OK, "0124", "전문가 소개 - 상담 가능 분야 조회 성공"),
    CONSULTANT_INFO_CATEGORY_UPDATE_SUCCESS(OK, "0125", "전문가 소개 - 상담 가능 분야 수정 성공"),
    CONSULTANT_INFO_REGION_SUCCESS(OK, "0126", "전문가 소개 - 상담 가능 지역 조회 성공"),
    CONSULTANT_INFO_REGION_UPDATE_SUCCESS(OK, "0127", "전문가 소개 - 상담 가능 지역 수정 성공"),
    /* 마이페이지 */
    MYPAGE_PASSWORD_SUCCESS(OK, "0128", "마이페이지 - 비밀번호 확인 성공"),
    MYPAGE_SUCCESS(OK, "0129", "마이페이지 - 조회 성공"),
    MYPAGE_UPDATE_SUCCESS(OK, "0130", "마이페이지 - 수정 성공"),
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
