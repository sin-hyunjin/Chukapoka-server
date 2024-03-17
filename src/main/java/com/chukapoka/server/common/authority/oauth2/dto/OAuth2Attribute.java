package com.chukapoka.server.common.authority.oauth2.dto;
import com.chukapoka.server.common.enums.EmailType;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder(access = AccessLevel.PRIVATE) // Builder 메서드를 외부에서 사용하지 않으므로, Private 제어자로 지정
@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes; // 사용자 속성 정보를 담는 Map
    private String attributeId; // 사용자 속성의 키 값
    private String emailType; //GOOGLE , NAVER
    private String email; // 이메일 정보
    private String name; //사용자 정보



    public static OAuth2Attribute of(String emailType, String userNameAttributeName, Map<String, Object> attributes) {
        switch (emailType) {
            case "google":
                return ofGoogle(userNameAttributeName, attributes);
            case "kakao":
                return ofKakao(emailType, userNameAttributeName, attributes);
            case "naver":
                return ofNaver(emailType, userNameAttributeName, attributes);
            default:
                throw new RuntimeException();
        }
    }

    /**
     *   Google 로그인일 경우 사용하는 메서드, 사용자 정보가 따로 Wrapping 되지 않고 제공되어,
     *   바로 get() 메서드로 접근이 가능하다.
     * */
    private static OAuth2Attribute ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes ) {
        return OAuth2Attribute.builder()
                .email((String) attributes.get("email"))
                .emailType(EmailType.GOOGLE.name())
                .attributes(attributes)
                .attributeId((String) attributes.get(userNameAttributeName))
                .name((String) attributes.get("name"))
                .build();
    }
    /**
     *   Kakao 로그인일 경우 사용하는 메서드, 필요한 사용자 정보가 kakaoAccount -> kakaoProfile 두번 감싸져 있어서,
     *   두번 get() 메서드를 이용해 사용자 정보를 담고있는 Map을 꺼내야한다.
     * */
    private static OAuth2Attribute ofKakao(String emailType, String userNameAttributeName,Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return OAuth2Attribute.builder()
                .email((String) kakaoAccount.get("email"))
                .emailType(EmailType.KAKAO.name())
                .attributes(kakaoAccount)
                .attributeId((String) kakaoAccount.get(userNameAttributeName))
                .name((String) attributes.get("name"))
                .build();
    }
    /*
     *  Naver 로그인일 경우 사용하는 메서드, 필요한 사용자 정보가 response Map에 감싸져 있어서,
     *  한번 get() 메서드를 이용해 사용자 정보를 담고있는 Map을 꺼내야한다.
     * */
    private static OAuth2Attribute ofNaver(String provider,  String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .email((String) response.get("email"))
                .emailType(provider)
                .attributes(response)
                .attributeId((String) response.get(userNameAttributeName))
                .build();
    }

    /** OAuth2User 객체에 넣어주기 위해서 Map으로 값들을 반환 */
    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeId);
        map.put("emailType", emailType);
        map.put("email", email);
        map.put("name", name);
        return map;
    }
}