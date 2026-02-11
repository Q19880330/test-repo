package com.green.car.repository;

import com.green.car.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarImageRepository extends JpaRepository<CarImage,Long> {

    List<CarImage> findByCarId(Long carId);
}
