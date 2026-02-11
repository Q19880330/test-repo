package com.green.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarAddDto {
    private String brand, model, color, registerNumber;//브랜드, 모델, 생상, 등록번호
    private int year, price;
    private Long dealerId;
}
