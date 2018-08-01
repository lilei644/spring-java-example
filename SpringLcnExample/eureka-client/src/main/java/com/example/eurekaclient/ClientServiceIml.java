package com.example.eurekaclient;

import com.codingapi.tx.annotation.TxTransaction;
import com.example.eurekaclient.impl.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceIml implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    @TxTransaction
    @Transactional
    public String consumer() {
        int result = clientMapper.updateUserInfo();
        return String.valueOf(result);
    }

}
