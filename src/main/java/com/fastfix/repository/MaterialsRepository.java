package com.fastfix.repository;

import com.fastfix.entity.Materials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialsRepository extends JpaRepository<Materials,Long> {

    @Query("SELECT COUNT(m) FROM Materials m")
    long countAllMaterials();
}
