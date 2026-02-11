package com.green.car.controller;


import com.green.car.dto.DealerRegDto;
import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.MemberLoginDto;
import com.green.car.dto.TokenDto;
import com.green.car.service.DealerRegService;
import com.green.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

    private final DealerRegService dealerRegService;

    //회원 로그인한 사람만 딜러 등록 가능
    //딜러 신청 게시글 등록
    //http://localhost:8081/member/register
    // request body에 담아준다.
    @PostMapping("/register")
    public String dealerRegRegister(@RequestBody DealerRegDto dto){
        try {
            dealerRegService.dealerRegSave(dto);
            return "ok";
        }catch (Exception e){
            return "fail";
        }
    }

    //////////////////////////////////////////////////////////test///////////////////////////////////////////////////////////////////
   /* @PostMapping("/test")
    public String test() {
        return "success";
    }

    @PostMapping("/usertest")
    public String usertest(){
        //SecurityContextHolder -> SecurityContext -> Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName() == null){
            throw new RuntimeException("인증 정보가 없습니다.");
        }
        //authentication.getName() -> email
        return authentication.getName();
    }*/

}