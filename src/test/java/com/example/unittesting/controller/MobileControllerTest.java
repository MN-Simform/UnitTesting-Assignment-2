package com.example.unittesting.controller;

import com.example.unittesting.entity.Mobile;
import com.example.unittesting.services.MobileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MobileController.class)
class MobileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MobileService mobileService;

    Mobile mobile1;
    Mobile mobile2;
    List<Mobile> mobileList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mobile1 = new Mobile(1, "Apple", "iPhone 12", 35000);
        mobile2 = new Mobile(2, "One Plus", "Nord C2", 20000);
        mobileList.add(mobile1);
        mobileList.add(mobile2);
    }

    @AfterEach
    void tearDown() {
        mobile1 = null;
        mobile2 = null;
        mobileList.clear();
    }

    @DisplayName("Test Show All Mobile List")
    @Nested
    class ShowAllMobile{

        @Test
        @DisplayName("Success Scenario : List Shown With Status Code 200")
        void testShowList_Success_StatusCode200() throws Exception {
            when(mobileService.showMobiles()).thenReturn(mobileList);
            mockMvc.perform(get("/mobiles"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Failed Scenario : List Doesn't Show With Status Code 404")
        void testShowList_Failed_StatusCode404() throws Exception {
            when(mobileService.showMobiles()).thenReturn(mobileList);
            mockMvc.perform(get("/mobiles/"))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Test Add Mobile")
    class AddMobile{

        @Test
        @DisplayName("Success Scenario : Mobile Added With Status Code 200")
        void testAddMobile_Success_StatusCode200() throws Exception {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
            String reqJson = objectWriter.writeValueAsString(mobile1);

            when(mobileService.addMobile(mobile1)).thenReturn("Success");
            MvcResult result = mockMvc.perform(post("/mobiles/addMobile")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqJson))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            Assertions.assertThat(result).isNotNull();
            String userJson = result.getResponse().getContentAsString();

            System.out.println("UserJason : " + userJson);
            Assertions.assertThat(userJson).isEqualTo("Mobile Added Successful");
        }

        @Test
        @DisplayName("Failed Scenario : Mobile Doesn't With Status Code 200")
        void testAddMobile_Failed_StatusCode200() throws Exception {

            Mobile mobile = new Mobile();

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
            String reqJson = objectWriter.writeValueAsString(mobile);

            when(mobileService.addMobile(mobile)).thenReturn("Failed");
            MvcResult result = mockMvc.perform(post("/mobiles/addMobile")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqJson))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            Assertions.assertThat(result).isNotNull();
            String userJson = result.getResponse().getContentAsString();

            System.out.println("UserJason : " + userJson);
            Assertions.assertThat(userJson).isEqualTo("Operation Failed");
        }

        @Test
        @DisplayName("Failed Scenario : Mobile Doesn't Added With Status Code 405")
        void testAddMobile_Failed_StatusCode405() throws Exception {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
            String reqJson = objectWriter.writeValueAsString(mobile1);

            when(mobileService.addMobile(mobile1)).thenReturn("Success");
            mockMvc.perform(post("/mobiles/addMobiles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqJson))
                    .andDo(print())
                    .andExpect(status().isMethodNotAllowed());
        }
    }

    @Nested
    @DisplayName("Test Find Mobile By Id")
    class FindById{

        @Test
        @DisplayName("Success Scenario : Mobile Fetched With Status Code 200")
        void testFindById_Success_StatusCode200() throws Exception {
            when(mobileService.findMobileId(2)).thenReturn((mobile2));
            mockMvc.perform(get("/mobiles/findById/2"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Failed Scenario : Mobile Doesn't Fetched With Status Code 404")
        void testFindById_Failed_StatusCode404() throws Exception {
            when(mobileService.findMobileId(3)).thenReturn(mobile1);
            mockMvc.perform(get("/mobiles/findMobileById/3"))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Test Find Mobile By Company Name")
    class TestFindByCompany {

        @Test
        @DisplayName("Success Scenario : Mobile Fetched By Company Name 'Apple' With Status Code 200")
        void testFindByCompany_Success_StatusCode200() throws Exception {
            when(mobileService.showMobileByCompany("Apple"))
                    .thenReturn(mobileList
                            .stream()
                            .filter(mobile -> mobile.getCompany().equalsIgnoreCase("Apple"))
                            .collect(Collectors.toList()));
            mockMvc.perform(get("/mobiles/Apple"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Failed Scenario : Mobile Doesn't Fetched By Company Name 'Samsung' With Status Code 200")
        void testFindByCompany_Failed_StatusCode404() throws Exception {
            when(mobileService.showMobileByCompany("Samsung"))
                    .thenReturn(mobileList
                            .stream()
                            .filter(mobile -> mobile.getCompany().equalsIgnoreCase("Samsung"))
                            .collect(Collectors.toList()));
            mockMvc.perform(get("/mobiles/Samsung"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
}