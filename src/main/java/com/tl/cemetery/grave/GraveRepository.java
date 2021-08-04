package com.tl.cemetery.grave;

import com.tl.cemetery.obituary.Obituary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GraveRepository extends JpaRepository<Grave, Long> {

    @Query(value = "select g from Grave g where g.name like :name% order by g.name, g.row, g.column")
    List<Grave> findAllGravesInParcel(@Param("name") String name);

    @Query(value = "select o from Obituary o join fetch Grave g on o.grave.id = g.id where o.grave.id = (select g.id from Grave g where g.name = :name and g.row = :row and g.column = :column)")
    List<Obituary> findGraveByCommand(@Param("name") String name, @Param("row") int row, @Param("column") int column);
}
