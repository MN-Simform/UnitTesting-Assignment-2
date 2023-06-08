package com.example.unittesting.repository;

import com.example.unittesting.entity.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileRepository extends JpaRepository<Mobile, Integer> {

    @Query("Select m from Mobile m where m.company=?1")
    public List<Mobile> findMobilesByCompany(String company);
}
