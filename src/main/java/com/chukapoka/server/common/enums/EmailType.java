package com.chukapoka.server.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum EmailType {

    // 등록된 이메일인지 확인 후 이메일타입 확인, 기본값은 default
    DEFAULT,
    GOOGLE,
    KAKAO,

}
