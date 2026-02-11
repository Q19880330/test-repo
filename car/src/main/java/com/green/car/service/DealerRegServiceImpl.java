package com.green.car.service;

import com.green.car.dto.DealerRegDto;
import com.green.car.dto.DealerRegListDto;
import com.green.car.entity.DealerRegister;
import com.green.car.repository.DealerRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealerRegServiceImpl implements DealerRegService{

    private final DealerRegisterRepository dealerRegisterRepository;


    //딜러 신청 게시글 등록 (dto -> entity)
    @Override
    public void dealerRegSave(DealerRegDto dto) {
        dealerRegisterRepository.save(dtoToEntity(dto));

    }

    @Override
    public List<DealerRegListDto> getDealerRegList() {
        List<DealerRegister> list = dealerRegisterRepository.findAll();
        List<DealerRegListDto> result = list.stream().map(entity->entityToDto(entity)).collect(Collectors.toList());
        return result;
    }
}
