package com.example.unittesting.services.impl;

import com.example.unittesting.entity.Mobile;
import com.example.unittesting.repository.MobileRepository;
import com.example.unittesting.services.MobileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MobileServiceImplTest {

    @Mock
    MobileRepository mobileRepository;

    MobileService mobileService;

    Mobile mobile;

    @BeforeEach
    void setUp() {
        mobileService = new MobileServiceImpl(mobileRepository);
        mobile = new Mobile(1, "Apple", "iPhone 14", 100000);
    }

    @AfterEach
    void tearDown() {
        mobile = null;
        mobileService = null;
    }

    @Test
    @DisplayName("Test Show All Mobiles")
    void testShowMobiles() {
        mock(Mobile.class);
        mock(MobileRepository.class);

        when(mobileRepository.findAll()).thenReturn(
                new ArrayList<Mobile>(Collections.singleton(mobile))
        );
        assertThat(mobileService.showMobiles().get(0)).isEqualTo(mobile);
    }

    @Test
    @DisplayName("Test Add Mobile")
    void testAddMobile() {
        mock(Mobile.class);
        mock(MobileRepository.class);

        when(mobileRepository.save(mobile)).thenReturn(mobile);
        assertThat(mobileService.addMobile(mobile)).isEqualTo("Success");
    }

    @Test
    @DisplayName("Test Find Mobile By Id")
    void testFindMobileId(){
        mock(Mobile.class);
        mock(MobileRepository.class);

        when(mobileRepository.findById(1)).thenReturn(Optional.ofNullable(mobile));
        assertThat(mobileService.findMobileId(1).getId()).isEqualTo(mobile.getId());
    }

    @Test
    @DisplayName("Test Find Mobile By Company Name")
    void testShowMobileByCompany(){
        mock(Mobile.class);
        mock(MobileRepository.class);

        when(mobileRepository.findMobilesByCompany("Apple")).thenReturn(
                new ArrayList<Mobile>(Collections.singleton(mobile))
        );
        assertThat(mobileService.showMobileByCompany("Apple").get(0)).isEqualTo(mobile);
    }
}