package com.green.car.controller;


import com.green.car.dto.*;
import com.green.car.service.CarService;
import com.green.car.service.DealerRegService;
import com.green.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dealer")
@CrossOrigin(origins = "http://localhost:3000")
public class DealerController {

    private final CarService carService;

    //딜러만 중고차량 등록가능
    @PostMapping("/addCar")
    public ResponseEntity addCar(CarDto dto,
                                 @RequestParam("uploadFiles") List<MultipartFile> uploadFiles
    ) {
        try {
            System.out.println(dto.toString());
            System.out.println(uploadFiles);
            carService.addCar(dto, uploadFiles);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    //딜러가 등록한 자동차 조회
    @GetMapping("/carList")
    public ResponseEntity listCar(@RequestParam("dealerId") Long dealerId){
        List<ListCarDto> result = carService.getDealercarList(dealerId);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    //수정
    @PostMapping("/carEdit")
    public ResponseEntity editCar(CarDto carDto){
        System.out.println(carDto.toString());
        try {
            carService.editCar(carDto);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("ok",HttpStatus.OK);

    }



}