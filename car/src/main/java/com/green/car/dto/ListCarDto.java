package com.green.car.dto;

import com.green.car.entity.Dealer;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListCarDto {
    private Long id;
    private String title;//매물제목
    private int year;//년식
    private String fuel; //연료
    private int price; //가격
    private Dealer dealer;// 딜러
    private String imgName;//이미지 경로
    private int mileage;//주행거리

}
