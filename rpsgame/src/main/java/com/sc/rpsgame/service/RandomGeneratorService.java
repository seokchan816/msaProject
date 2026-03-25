package com.sc.rpsgame.service;

import org.springframework.stereotype.Service;

import com.sc.rpsgame.enums.RoPaSis;

@Service
public class RandomGeneratorService {
    public RoPaSis getRoPaSis(){
        return RoPaSis.getRps();
    }
}
