package com.green.car.service;

import com.green.car.dto.MemberDto;
import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.TokenDto;
import com.green.car.entity.Member;
import com.green.car.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Member savemember(MemberJoinDto dto) {
        //사용자가 전달한 dto를 엔티티로 변환
        Member member = dtoToentity(dto);
        //패스워드 필드는 전달한 패스워드 값을 암호화 하여 할당
        member.setPassword(passwordEncoder.encode(dto.getPassword()));
        return memberRepository.save(member);
    }

    @Override
    public String validateDuplicationMember(MemberJoinDto dto) {
        Member findMember = memberRepository.findByEmail(dto.getEmail());
        //이미 동일한 이메일을 가진 member가 존재함
        if (findMember!=null){
            return "ok";
        }
        return "fail";
    }

    //로그인 구현
    @Override
    public TokenDto login(String memberId, String password) throws BadCredentialsException {
        // 1. 로그인 요청시 전달된 memberId(email)과 password를 기반으로
        // Authentication객체 생성
        // authenticate()메소드를 통해 요청된 Member에 대한 검증 진행
        //->loadUserByUsername메소드가 실행됨 (Security가 실행 )
        //검증에 성공하면 Authentication객체를 기반으로 JWT토큰 생성
        //1. 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberId, password);
        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든
        ///loadUserByUsername 메서드 실행
        //authenticate 메소드가 실행 될때 요청된 Member에 대한 검증 진행
        //CustomUserDetailsService에서 구현한 loadUserByUsername 메소드 실행
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        Member member = memberRepository.findByEmail(memberId);
        tokenDto.setEmail(memberId);
        tokenDto.setRole(member.getRole());
        tokenDto.setDealerId(member.getDealerId());
        tokenDto.setMemberId(member.getId());

        return tokenDto;
    }

    //회원목록조회
    @Override
    public List<MemberDto> getMemberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberDto> result = members.stream().map(arr->entityToDto(arr)).collect(Collectors.toList());
        return result;
    }
}
