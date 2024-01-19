package com.chukapoka.server.common.enums;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ResultType {
    SUCCESS("정상 처리 되었습니다."),
    ERROR("에러가 발생했습니다.");

    private String msg;
    private static final Map<String, ResultType> lookup = new HashMap<>();

    static {
        for (ResultType resultCode : ResultType.values()) {
            lookup.put(resultCode.getMsg(), resultCode);
        }
    }

    ResultType(String msg) {
        this.msg = msg;
    }

    public static ResultType getByMsg(String msg) {
        return lookup.get(msg);
    }
}

