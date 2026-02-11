package com.green.car.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "dealerReg_seq", sequenceName = "dealerReg_seq", allocationSize = 1, initialValue = 1)
public class DealerRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "dealerReg_seq")
    @Column(name = "dealer_reg_id")
    private Long id;
    private String name;
    private String phone;
    private String location;
    private String message;
    private Long memberId;
}
