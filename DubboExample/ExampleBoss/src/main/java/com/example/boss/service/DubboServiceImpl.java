package com.example.boss.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.common.bean.ContentBean;
import com.example.common.service.CommonService;
import org.springframework.stereotype.Service;

@Service
public class DubboServiceImpl {

    @Reference(version = "1.0.0", timeout = 10000)
    private CommonService commonService;

    public String sayHello() {
        ContentBean contentBean = commonService.sayHello();
        return "hello name:" + contentBean.getName() + " --- age: " + contentBean.getAge();
    }

}
