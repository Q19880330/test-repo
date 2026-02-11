package com.green.car.controller;

import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.MemberLoginDto;
import com.green.car.dto.TokenDto;
import com.green.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/site")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class SiteController {

    private final MemberService memberService;

    //로그인
    @PostMapping("/login")
    public TokenDto login(@RequestBody MemberLoginDto memberLoginDto){
        String username = memberLoginDto.getUsername();
        String password = memberLoginDto.getPassword();
        TokenDto token =  memberService.login(username,password);
        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든
        // loadUserByUsername 메서드 실행
        return token;
    }
    //{"username":"dh1236@naver.com","password":"asd"}
    //Bearer

    //회원가입
    @PostMapping("/join")
    public String memberjoin(@RequestBody MemberJoinDto memberJoinDto){
        try {
            memberService.savemember(memberJoinDto);
            return "ok";
        }catch (Exception e){
            return "fail";
        }
    }
}
