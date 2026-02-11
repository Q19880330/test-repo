package com.green.car.service;

import com.green.car.dto.DealerRegDto;
import com.green.car.dto.DealerRegListDto;
import com.green.car.entity.Dealer;
import com.green.car.entity.DealerRegister;

import java.util.List;

public interface DealerRegService {
    //등록하기
    void dealerRegSave(DealerRegDto dto);

    //조회하기
    List<DealerRegListDto> getDealerRegList();

    //default
    //dto -> entity
    default DealerRegister dtoToEntity (DealerRegDto dto){
        DealerRegister dealerRegister = DealerRegister.builder()
                .phone(dto.getPhone())
                .location(dto.getLocation())
                .name(dto.getName())
                .message(dto.getMessage())
                .memberId(dto.getMemberId())
                .build();
        return dealerRegister;
    }

    ////////////////////////////////////////////////
    default DealerRegListDto entityToDto(DealerRegister dealerRegister){
        DealerRegListDto dto = DealerRegListDto.builder()
                .id(dealerRegister.getId())
                .name(dealerRegister.getName())
                .phone(dealerRegister.getPhone())
                .location(dealerRegister.getLocation())
                .message(dealerRegister.getMessage())
                .memberId(dealerRegister.getMemberId())
                .build();
        return dto;
    }

}
