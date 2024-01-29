package com.chukapoka.server.user.sevice;

import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.user.dto.AuthNumberResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthNumberService {

    private final JavaMailSender javaMailSender;
    private static final String EMAIL_SUBJECT = "Chukapoka 회원가입 인증번호 확인 부탁드립니다.";

    @Value("${spring.mail.username}")
    String SENDER_EMAIL;

    // 인증번호 6자리 무작위 생성
    public String createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int idx = random.nextInt(10); // 0 ~ 9 숫자 생성
            key.append(idx);
        }

        return key.toString();
    }

    // 메일 양식 작성
    public MimeMessage createEmailForm(String email, String authNum) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        // 수신자 설정
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        // 발신자 이메일 설정
        message.setFrom(SENDER_EMAIL);
        message.setSubject(EMAIL_SUBJECT);
        String msgOfEmail = buildEmailContent(authNum);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }

    // 실제 메일 전송
    public AuthNumberResponseDto sendEmail(String email) throws MessagingException, UnsupportedEncodingException {
        String authNum = createCode();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMinutes(5); // 유효시간은 5분

        AuthNumberResponseDto responseDto = new AuthNumberResponseDto(
                ResultType.SUCCESS,
                email,
                authNum,
                createdAt,
                expiredAt
        );

        // 메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(email, authNum);
        // 실제 메일 전송
        javaMailSender.send(emailForm);

        return responseDto; // 인증 코드 반환
    }

    // 이메일 폼
    private String buildEmailContent(String authNum) {
        return STR."<div style='margin:20px;'> <h1> Chukapoka 이메일 인증번호 입니다.</h1><br><p>아래 인증번호를 입력해주세요<p><br><div align='center' style='border:1px solid black; font-family:verdana';><h3 style='color:blue;'>회원가입 이메일 인증번호입니다.</h3><div style='font-size:130%'>CODE : <strong>\{authNum}</strong><div><br/> </div>";
    }
    
}
