package com.example.boss.service;

import com.example.common.bean.ContentBean;
import com.example.common.service.CommonService;
import com.reger.dubbo.annotation.Inject;
import org.springframework.stereotype.Service;

@Service
public class DubboServiceImpl {

    @Inject
    private CommonService commonService;

    public String sayHello() {
        ContentBean contentBean = commonService.sayHello();
        return "hello name:" + contentBean.getName() + " --- age: " + contentBean.getAge();
    }

}
