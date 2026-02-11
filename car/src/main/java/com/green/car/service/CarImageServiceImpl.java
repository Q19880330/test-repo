package com.green.car.service;

import com.green.car.dto.CarAddDto;
import com.green.car.dto.CarImageDto;
import com.green.car.entity.Car;
import com.green.car.entity.CarImage;
import com.green.car.repository.CarImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarImageServiceImpl implements CarImageService{

    private final FileService fileService;
    private final CarImageRepository carImageRepository;
    @Value("${carImgLocation}")
    private String carImageLocation;


    @Override
    public void saveCarImage(CarImage carImage, MultipartFile carImgFile) throws Exception {
        //carImage.getBytes() : 파일을 바이트 형식의 배열로 변환
        //file.getOriginalFilename() : 파일의 원래 이름 리턴
        //uploadFile(경로, 원본 이미지 이름, 파일(바이트타입))
        //원본이름
        String oriImgName = carImgFile.getOriginalFilename();
        //파일 업로드 uploadFile을 호출하면 경로에 이미지 업로드 되고 저장된 파일 이름을 리턴함

        //파일 업로드
        //파일이 없을때만 등록
        if(!StringUtils.isEmpty(oriImgName)){
            //imgName : uuid.jpg (바꿔서 저장된 이름)
            String imgName = fileService.uploadFile(carImageLocation, oriImgName, carImgFile.getBytes());
            //imgUrl : 경로 + 저장된 파일 이름
            String imgUrl = "/images/carimg/"+imgName;
            //엔티티 필드값 입력
            carImage.update(oriImgName, imgName, imgUrl);
            //엔티티 영속 저장
            carImageRepository.save(carImage);
        }

    }

    //carId로 carImage조회
    @Override
    public List<CarImageDto> findByCarId(Long carId) {
        List<CarImage> result = carImageRepository.findByCarId(carId);
        //엔티티가 담긴 리스트를 dto가 담긴 리스트로 반환
        List<CarImageDto> carImageDtos = result.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
        return carImageDtos;
    }
}
