package com.green.car.repository;

import com.green.car.entity.Maker;
import com.green.car.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    @Query("select m from Model m where m.maker.id = :id")
    List<Model> getModelList(@Param("id") Long id);
}
