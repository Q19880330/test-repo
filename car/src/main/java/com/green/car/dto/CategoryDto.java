package com.green.car.dto;

import com.green.car.entity.Category;
import com.green.car.entity.Maker;
import com.green.car.entity.Model;
import lombok.*;

import java.util.List;

@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private List<Category> categories;
    private List<Maker> makers;
    private List<Model> models;

}
