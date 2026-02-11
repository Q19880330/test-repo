package com.green.car.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "car_seq", sequenceName = "car_seq", allocationSize = 1)
public class Car extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "car_seq")
    @Column(name = "car_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maker_id")
    private Maker maker;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;
    //기본정보 년식, 배기량, 주행거리, 변속기, 연료, 색상, 가격, 차량등록번호
    private String title;//매물제목
    private String cardesc;//설명
    private int displacement; //배기량
    private int mileage;//주행거리
    private String transmission; //변속기
    private String fuel; //연료
    private String color, registerNumber;// 색상, 등록번호
    private int year, price;//년식, 가격


}
