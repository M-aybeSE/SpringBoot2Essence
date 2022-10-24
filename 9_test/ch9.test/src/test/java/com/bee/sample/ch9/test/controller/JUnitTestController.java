package com.bee.sample.ch9.test.controller;

import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author linhx
 * @date 2022-10-21 15:47
 */
public class JUnitTestController {

    /**
     * 需要为静态方法
     */
    @BeforeClass
    public static void beforeClassTest() {
        System.out.println("单元测试开始之前执行初始化......");
    }


    public void beforeTest() {
        System.out.println("单元测试方法开始之前执行.....");
    }

    @Test
    public void test1() {
        System.out.println("开始测试.....");
    }

    @Test
    public void test2() {
//        DateFormatUtils
        Assert.assertEquals("", 1, 1);
        assertArrayEquals("", Arrays.array(1), Arrays.array(2));
    }

    @After
    public void afterTest() {
        System.out.println("单元测试方法结束后执行......");
    }

    @AfterClass
    public static void afterClassTest() {
        System.out.println("单元测试结束之后执行......");
    }

}
