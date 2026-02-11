package com.green.car.service;

import com.green.car.dto.MemberJoinDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("insert member")
    public void insertMember(){
        MemberJoinDto dto = MemberJoinDto.builder()
                .name("이수정")
                .email("dh1236@naver.com")
                .address("울산 남구")
                .password("asd")
                .build();
        memberService.savemember(dto);
    }


}
