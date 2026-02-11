package com.green.car.controller;

import com.green.car.dto.*;

import com.green.car.entity.Category;
import com.green.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    //http://localhost:8081/car/category?category="+id+"&maker="+maker
    @GetMapping("/category")
    public ResponseEntity getCategory(@RequestParam("category") Long categoryId,
                                      @RequestParam("maker") Long makerId
    ) {
        System.out.println("카티고리 아이디는 " + categoryId);
        System.out.println("메이커 아이디는 " + makerId);
        CategoryDto result = carService.getCategory(categoryId, makerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //메인 카이스트 조회 {categoryId : 3} requestDto 필드 자동 매핑
    //요청시 RequestDto 타입을 받고
    //응답시 ResultDto 타입으로 응답
    @RequestMapping("/cars")
    public ResponseEntity getCars(RequestDto requestDto) {
        //자동차를 검색해서 반환
        ResultDto<MainCarDto, Object[]> result = carService.carMainlist(requestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //중고차 목록페이지
    @GetMapping("/carList")
    public ResponseEntity getCarList(
            @RequestParam("categoryId") Long id,
            @RequestParam("maker") String maker,
            @RequestParam("model") String model
    ) {
        System.out.println("================================================메이커 값은 : " + maker);
        System.out.println("================================================모델 값은 : " + model);
        List<ListCarDto> result = carService.getpageList(id, maker, model);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/image")
    public ResponseEntity<?> returnImage(@RequestParam("image") String image) {
        String path = "C:\\car\\image\\";//이미지가 저장된 위치
        Resource resource = new FileSystemResource(path + image);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    //상세보기 
    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> carDetail(@PathVariable("carId") Long carId) {
        CarDto carDto = carService.findById(carId);
        return new ResponseEntity<>(carDto, HttpStatus.OK);
    }



}
