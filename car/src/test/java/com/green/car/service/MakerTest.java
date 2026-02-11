package com.green.car.service;

import com.green.car.entity.Maker;
import com.green.car.entity.Model;
import com.green.car.repository.MakerRepository;
import com.green.car.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MakerTest {
    //의존주입
    @Autowired
    public MakerRepository makerRepository;
    @Autowired
    public ModelRepository modelRepository;
    //테스트 메소드 어노테이션
    @Test
    public void getList(){
        List<Maker> result = makerRepository.getList(1L);
        for (Maker m : result){
            System.out.println("국산차"+m.toString());
        }
        List<Maker> result2 = makerRepository.getList(2L);
        for (Maker m : result2){
            System.out.println("수입차"+m.toString());
        }
    }
    @Test
    public void gerModelList(){
        List<Model>result = modelRepository.getModelList(1L);
        for (Model m : result){
            System.out.println("현대 ================== "+ m.toString());
        }
        List<Model>result2 = modelRepository.getModelList(2L);
        for (Model m : result2){
            System.out.println("제네시스 ================== "+ m.toString());
        }

    }
}
