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
@SequenceGenerator(name="category_seq", sequenceName = "category_seq", initialValue = 1,allocationSize = 1)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @Column(name="category_id")
    private Long id;
    private String categoryName;
}