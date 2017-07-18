package com.example.demo.handler;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by LiLei on 2017/7/18.
 */
public class SenderTest {


    @Test
    public void send() throws Exception {
        Sender sender = new Sender();
        sender.send();

    }

}