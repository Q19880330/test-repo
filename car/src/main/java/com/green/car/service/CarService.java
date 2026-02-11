package com.green.car.service;

import com.green.car.dto.*;
import com.green.car.entity.Car;
import com.green.car.entity.CarImage;
import com.green.car.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {
    void  addCar(CarDto dto, List<MultipartFile> carImgFileList) throws Exception;
    Long carRemove(Long id);
    CarDto findById(Long id);
    //메인페이지 목록 조회하기
    ResultDto<MainCarDto, Object[]> carMainlist(RequestDto dto);
    //차 목록 페이지 조회하기
    List<ListCarDto> getpageList(Long id, String maker,String model);

    public CategoryDto getCategory(Long categoryId, Long makerId);

    //딜러 아이디 가지고 해당 딜러의 등록 자동차 출력
    List<ListCarDto> getDealercarList(Long dealerId);

    //등록자동차 정보 수정하기
    void editCar(CarDto dto);
    default Car dtoToEntity(CarDto dto){
        Car car = Car.builder()
                .title(dto.getTitle())
                .price(dto.getPrice())
                .cardesc(dto.getCardesc())
                .displacement(dto.getDisplacement())
                .mileage(dto.getMileage())
                .transmission(dto.getTransmission())
                .fuel(dto.getFuel())
                .year(dto.getYear())
                .color(dto.getColor())
                .registerNumber(dto.getRegisterNumber())
                .build();
        return car;
    }
    List<MainCarDto> getCarList();
    //엔티티 오브젝트를 MainCarDto로 변환하기
    default MainCarDto entityToObjectDto(Car car, CarImage carImage){
        MainCarDto mainCarDto = MainCarDto.builder()
                .id(car.getId())
                .title(car.getTitle())
                .price(car.getPrice())
                .makerName(car.getMaker().getMakerName())
                .imgName(carImage.getImgName())
                .build();
        return mainCarDto;
    }
}
