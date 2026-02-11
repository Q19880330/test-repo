package com.green.car.service;

import com.green.car.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtTokenProvider {
    //상수 변수 생성시 초기화 해줘야함
    private final Key key;

    //서버에서 발급한 토큰이 맞는지 확인 용으로 서명을한다.
    // 서명이 있는 토큰은 내가 발급한거니 권한을 줄 수 있도록 한다.
    // 토큰 위,변조를 방지하기위한 방식
    //생성자 secretKey를 디코딩하여 JWT 서명에 사용할 키를 생성
    //application properties에서 만들어준 키값 받아오는 방식
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        //주입된 secretKey를 BASE64로 디코딩하여 바이트 배열로 반환
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        //바이트 배열을 사용하여 hmacShaKey를 생성 --> JWT서명을 생성하고 검증
        this.key = Keys.hmacShaKeyFor(keyByte);
    }

    //method
    //유저 정보를 가지고 AccessToken, RefreshToken을 생성하는 Method
    public TokenDto generateToken(Authentication authentication){
        //stateLess 세션에 넣었다가 삭제한다.
        //따라서 서버에 무리가 없다.
        //권한생성 = 로그인 되어 권한을 받았다.

        //권한
        String authorities = authentication.getAuthorities().stream()//사용자가 가진 권한(USER,ADMIN,DEALER)을 추출
                .map(GrantedAuthority::getAuthority)//추출한 권한을 추출해서 문자열 컬렉션을 추출
                //람다식 구문 :: <- 메소드 호출 (getAuthority 메소드를 사용해서 변환하겠다.)
                .collect(Collectors.joining(","));//하나의 문자열로 리턴 USER,ADMIN

        //만료일 생성
        Long now = (new Date().getTime());//현재데이트 객체를 밀리초로 리턴
        Date accessTokenExpiresIn = new Date(now+86400000); //현재시간에 + 1일(86400000밀리초) 토큰의 유효기간

        //access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())//토큰주제 사용자 식별자(ex 로그인한 사용자의 이메일)
                .claim("roll",authorities) //사용자의 권한정보 추가(USER OR ADMIN OR DEALER) Claims에 담는듯?
                .setExpiration(accessTokenExpiresIn) //토큰의 만료시간 설정 (해당시점부터 1일)
                .signWith(key, SignatureAlgorithm.HS256)//서명 추가 (토큰 위변조방지, 토큰 무결성보장)
                .compact();//문자열로 반환

        //Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .grantType("Bearer")//토큰의 타입 : JWT 혹은 OAuth에 대한 토큰을 사용한다. (RFC 6750)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //JWT 복호화하여 토큰에 들어있는 정보를 꺼내는 메소드
    //Authentication리턴
    //Authentication  스프링 시큐리티에서 인증된 정보를 담고있는 인터페이스
    //accessToken을 전달받아 Authentication객체를 리턴
    public Authentication getAuthentication(String accessToken){
        //토큰 복호화
        Claims claims = ParseClaims(accessToken);
        if(claims.get("roll")==null){
            throw new RuntimeException("권한정보가 없는 토큰입니다.");
        }
        //권한정보가 있는사람
        //제네릭중에 GrantedAuthority을 상속받는 타입만 받겠다.
        Collection<? extends GrantedAuthority> authorities =
                //"USER,ADMIN"
                Arrays.stream(claims.get("roll").toString().split(","))
                        .map(SimpleGrantedAuthority::new)//변형람다식 생성자 호출
                        //람다식 구문 ::mew <- SimpleGrantedAuthority 생성자 호출 (생성자를 사용해서 변환하겠다.)
                        .collect(Collectors.toList());//권한이 담긴 배열로 받겠다.
        UserDetails principal = new User(claims.getSubject(),"",authorities);
        //Authentication 타을 리턴하기 위한 과정
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    //토큰 정보를 검증하는 메소드
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()//JWT 파싱하는 빌더를 생성
                    .setSigningKey(key)// 서명키를 설정, JWT를 검증할 때 사용
                    .build()//jwtparser객체 생성(빌드)
                    .parseClaimsJws(token);//주어진 토큰을 파싱하여 서명을 확인하고 JWT본문을 반환
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("유효하지 않는 JWT ",e);
        }catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰");
        }catch (UnsupportedJwtException e){
            log.info("입증되지 않는 JWT 토큰");
        }catch (IllegalArgumentException e){
            log.info("JWT claims가 비어있습니다.",e);
        }
        return false;

    }

    //사용자가 access토큰을 가지고 접근 할 때 사용한다.
    //주어진 accessToken을 해독하고 claim객체를 리턴함
    public Claims ParseClaims(String accessToken){
        //파싱시에는 예외가 발생할 수 있다.
        try {
            return Jwts.parserBuilder()//JWT를 파싱하는 빌더를 사용
                    .setSigningKey(key)//서명키를 설정 JWT검증시 사용
                    .build()
                    .parseClaimsJws(accessToken)//엑세스 토큰을 파싱해서 셔명을 확인
                    .getBody();// JWT본문을 얻는다. Claims를 반환
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
