package com.green.car.dto;

import com.green.car.entity.Category;
import com.green.car.entity.Dealer;
import com.green.car.entity.Maker;
import com.green.car.entity.Model;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDto {

    private Long id;
    private Long categoryId;
    private Long makerId;
    private Long modelId;
    private Long dealerId; //딜러 아이디
    private List<CarImageDto> carImageDtos = new ArrayList<>();
    //기본정보 년식, 배기량, 주행거리, 변속기, 연료, 색상, 가격, 차량등록번호
    private int displacement; //배기량
    private int mileage;//주행거리
    private String transmission; //변속기
    private String fuel; //연료
    private String color, registerNumber;// 색상, 등록번호
    private int year, price;//년식, 가격
    private String title;//매물제목
    private String cardesc;//설명
    private Dealer dealer;// 딜러


    //method
    public void update (Long categoryId, Long modelId, Long makerId, Long dealerId,  Long id,
                        String registerNumber, String transmission, String fuel, String title, String cardesc,String color,
                        int year, int price, int displacement, int mileage ){
        this.categoryId = categoryId;
        this.modelId =modelId;
        this.color =color;
        this.registerNumber =registerNumber;
        this.year =year;
        this.price =price;
        this.id =id;
        this.makerId = makerId ;
        this.displacement = displacement ;
        this.mileage = mileage ;
        this.transmission = transmission ;
        this.fuel = fuel;
        this.cardesc = cardesc;
        this.title = title;
        this.dealerId =dealerId;

    }

}
