package com.green.car.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "carimg_seq", sequenceName = "carimg_seq",allocationSize = 1, initialValue = 1)
public class CarImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "carimg_seq")
    @Column(name = "car_img_id")
    private Long id;
    private String imgName;     //이미지 이름
    private String oriImgName;  //원본이름
    private String imgUrl;      //경로
    private String repImgYn;    //대표이미지 여부
    //다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    //Method
    public void update(String oriImgName,String imgName,String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
