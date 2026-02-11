package com.green.car.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name="model_seq", sequenceName = "model_seq" , allocationSize = 1, initialValue = 1)
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model_seq")
    @Column(name="model_id")
    private Long id;
    private String modelName;
    @ManyToOne
    @JoinColumn(name = "maker_id")
    private Maker maker;
}
