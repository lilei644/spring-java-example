package com.example.eurekaconsumer.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.example.eurekaconsumer.ClientService;
import com.example.eurekaconsumer.ConsumerService;
import com.example.eurekaconsumer.mapper.ConsumerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private ClientService clientService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional
    public String consumer() {
         long now = System.currentTimeMillis();
        String result = clientService.consumer();
         System.out.println(">>>>>>>>>>" + (System.currentTimeMillis() - now));
        result += consumerMapper.updateUserInfo();
        System.out.println(">>>>>>>>>>" + (System.currentTimeMillis() - now));
        int i = 0/0;
        return result;
    }


}
