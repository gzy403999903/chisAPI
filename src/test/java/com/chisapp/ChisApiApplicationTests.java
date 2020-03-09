package com.chisapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChisApiApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("hello");
    }


    @Test
    public void test1() {
        BigDecimal b = new BigDecimal("2");
        int i = 10;
        System.out.println(b.add(BigDecimal.valueOf(i)));
    }

}
