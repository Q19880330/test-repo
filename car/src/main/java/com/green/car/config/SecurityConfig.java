package com.green.car.config;

import com.green.car.service.JwtTokenProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //httpSecurity를 구성하여 보안 설정을 정의
        //csrf(Cross-site Request Forgery) 보안을 비활성화
        //sessionManagement JWT을 사용하므로 세션을 사용하지 않는다.
        httpSecurity
                .csrf(cs-> cs.disable())
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 요청에 대한 인가 규칙 설정(페이지를 막는게 아니라 요청이 왔을때 막아주는 기능 )
        httpSecurity.authorizeHttpRequests((auth)->auth
                .requestMatchers("/dealer/**").hasRole("DEALER")
                .requestMatchers("/member/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/site/**").permitAll()
                .requestMatchers("/car/**").permitAll()
                .anyRequest().authenticated());
        //필터추가
        //jwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter앞에 추가하여
        //JWT인증을 처리하도록 필터를 추가함.
        httpSecurity.addFilterBefore(new jwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
