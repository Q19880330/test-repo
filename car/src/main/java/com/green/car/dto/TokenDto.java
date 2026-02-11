package com.green.car.dto;

import com.green.car.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    //클라이언트에게 전달할 토큰
    private String grantType;       // 어느정도의 권한을 줄지 결정하는 자리인듯??
    private String accessToken;     //사용가능한 시간이 정해지는 듯???
    private String refreshToken;    //만료시 사용할 토큰
    private String email;           //이메일
    private Role role;
    private Long dealerId;
    private Long memberId;


}
