package com.chukapoka.server.treemember.domain;

public class TreeUserEnumType{

    // 등록된 이메일인지 확인 후 이메일타입 확인, 기본값은 Default
    public enum EmailType {
        Default,
        Google,
        Kakao
    }
    // 성공, 실패
    public enum ResultType {
        Success,
        Fail
    }

}


