package com.green.car.dto;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDealerDto {
    private String name;
    private String phone;
    private String location;
    private Long memberId;
}
