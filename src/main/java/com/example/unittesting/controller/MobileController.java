package com.example.unittesting.controller;

import com.example.unittesting.entity.Mobile;
import com.example.unittesting.services.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mobiles")
public class MobileController {

    @Autowired
    MobileService mobileService;

    @GetMapping
    @ResponseBody
    public List<Mobile> showMobiles(){
        return mobileService.showMobiles();
    }

    @PostMapping("/addMobile")
    public String addMobile(@RequestBody Mobile mobile){
        if(mobile.getModel() != null){
            mobileService.addMobile(mobile);
            return "Mobile Added Successful";
        }
        return "Operation Failed";
    }

    @GetMapping("/findById/{id}")
    public Mobile findById(@PathVariable int id){
        return mobileService.findMobileId(id);
    }

    @GetMapping("/{company}")
    public List<Mobile> findMobileByCompany(@PathVariable String company){
        return mobileService.showMobileByCompany(company);
    }
}
