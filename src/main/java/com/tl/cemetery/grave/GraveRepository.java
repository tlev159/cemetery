package com.tl.cemetery.grave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GraveRepository extends JpaRepository<Grave, Long> {

    @Query(value = "select g from Grave g where g.name like :name")
    List<Grave> findAllGravesInParcel(@Param("name") String name);

}
