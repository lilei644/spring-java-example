package com.example.work.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.common.bean.ContentBean;
import com.example.common.service.CommonService;

@Service(version = "1.0.0", timeout = 10000)
public class CommonServiceImpl implements CommonService {

    @Override
    public ContentBean sayHello() {
        return new ContentBean(25, "Mark");
    }
}
