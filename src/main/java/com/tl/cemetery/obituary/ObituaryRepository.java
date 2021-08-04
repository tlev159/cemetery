package com.tl.cemetery.obituary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ObituaryRepository extends JpaRepository<Obituary, Long> {

    @Query(value = "select o from Obituary o where o.grave.id = :id order by o.name")
    List<ObituaryDTO> findAllInGraveWithGraveId(@Param("id") Long id);

    @Query(value = "select o from Obituary o where o.name = :name order by o.name")
    Optional<Object> findAllOrByName(@Param("name") Optional<String> name);

}
