package com.example.unittesting.services.impl;

import com.example.unittesting.entity.Mobile;
import com.example.unittesting.repository.MobileRepository;
import com.example.unittesting.services.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobileServiceImpl implements MobileService {

    @Autowired
    MobileRepository mobileRepository;

    public MobileServiceImpl(MobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }

    @Override
    public List<Mobile> showMobiles() {
        return mobileRepository.findAll();
    }

    @Override
    public String addMobile(Mobile mobile) {
        if(mobile.getModel() != null){
            mobileRepository.save(mobile);
            return "Success";
        }
        return "Failed";
    }

    @Override
    public Mobile findMobileId(Integer id) {
        if(mobileRepository.findById(id).isEmpty())
            throw new NullPointerException("Mobile Doesn't Exist");
        return mobileRepository.findById(id).get();
    }

    @Override
    public List<Mobile> showMobileByCompany(String company) {
        return mobileRepository.findMobilesByCompany(company);
    }
}
