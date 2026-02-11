package com.green.car.dto;

import com.green.car.entity.Dealer;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainCarDto {
    private Long id;
    private int price;
    private String makerName;
    private String imgName;
    private String title;
}
