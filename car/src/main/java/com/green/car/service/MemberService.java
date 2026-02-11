package com.green.car.service;

import com.green.car.constant.Role;
import com.green.car.dto.MemberDto;
import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.TokenDto;
import com.green.car.entity.Member;

import java.util.List;

public interface MemberService {
    //회원가입
    Member savemember(MemberJoinDto dto);
    //email 중복체크
    String validateDuplicationMember(MemberJoinDto dto);
    //로그인하기
    TokenDto login(String memberId, String password);
    //회원 목록 조회
    List<MemberDto> getMemberList();

    //dto 전달받아 entity로 변환하는 함수
    default Member dtoToentity(MemberJoinDto dto){
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .role(Role.USER)
                .build();
        return member;
    }

    default MemberDto entityToDto (Member member){
        MemberDto dto = MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .dealerId(member.getDealerId())
                .role(member.getRole())
                .build();
        return dto;
    }

}
