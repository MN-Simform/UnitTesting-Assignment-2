package com.example.unittesting.repository;

import com.example.unittesting.entity.Mobile;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
class MobileRepositoryTest {

    @Autowired
    MobileRepository mobileRepository;

    Mobile mobile;

    @BeforeEach
    void setUp() {
        mobile = new Mobile(1, "Apple", "iPhone 12", 60000);
        mobileRepository.save(mobile);
    }

    @AfterEach
    void tearDown() {
        mobile = null;
        mobileRepository.deleteAll();
    }

    @Test
    @DisplayName("Test Find Mobile By Company Success Scenario")
    void testFindMobilesByCompany_Success() {
        List<Mobile> mobileList = mobileRepository.findMobilesByCompany("Apple");
        assertEquals(mobileList.get(0).getCompany(), mobile.getCompany());
        assertEquals(mobileList.get(0), mobile);
        mobileList.forEach(mobile1 -> log.info(mobile1.toString()));
    }

    @Test
    @DisplayName("Test Find Mobile By Company Failed Scenario")
    void testFindMobileByCompany_Failed() {
        List<Mobile> mobileList = mobileRepository.findMobilesByCompany("Samsung");
        Assertions.assertThat(mobileList).isNotNull();
        assertTrue(mobileList.isEmpty());
    }

    @Test
    @DisplayName("Test Find All Method : Count All Method")
    public void testMobileCount_findAll() {
        int count = 1;
        for (int i = 0; i < 5; i++) {
            Mobile mobile = new Mobile(count++, "Abc", "Xyz", 100);
            mobileRepository.save(mobile);
        }
        List<Mobile> allMobiles = mobileRepository.findAll();
        assertEquals(5, allMobiles.size());
        allMobiles.forEach(mobile1 -> log.info(mobile1.toString()));
    }

    @Test
    @DisplayName(" Test Save Method : Add Mobile")
    public void testAddMobile_saveMethod() {
        mobile = new Mobile(2, "MI", "Note 8 Pro", 15000);
        Mobile addMobile = mobileRepository.save(mobile);
        assertEquals(addMobile.getId(), 2);
        log.info(mobile.toString());
    }
}