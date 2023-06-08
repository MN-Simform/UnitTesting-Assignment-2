package com.example.unittesting.services;

import com.example.unittesting.entity.Mobile;

import java.util.List;

public interface MobileService {
    public List<Mobile> showMobiles();

    public String addMobile(Mobile mobile);

    public Mobile findMobileId(Integer id);

    public List<Mobile> showMobileByCompany(String company);
}
