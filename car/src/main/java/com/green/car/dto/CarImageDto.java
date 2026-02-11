package com.green.car.dto;

import com.green.car.entity.Car;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarImageDto {
    private Long id;
    private String imgName;     //이미지 이름
    private String oriImgName;  //원본이름
    private String imgUrl;      //경로
    private String repImgYn;    //대표이미지 여부
}
