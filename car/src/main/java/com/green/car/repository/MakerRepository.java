package com.green.car.repository;

import com.green.car.entity.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MakerRepository extends JpaRepository<Maker, Long> {

    @Query("select m from Maker m where m.category.id = :categoryId")
    List<Maker> getList(@Param("categoryId") Long categoryId);
}
