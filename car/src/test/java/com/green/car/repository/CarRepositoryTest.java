package com.green.car.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;

    @Test
    @Transactional
    public void listCar(){
        List<Object[]> carlist = carRepository.getCarList();
        for (Object[] obj : carlist){
            System.out.println(Arrays.toString(obj));
        }
    }
}
