package com.bee.sample.ch9.test.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author linhx
 * @date 2022-10-24 10:24
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({JUnitTestController.class, UserControllerTest.class})
public class TestSuitMain {

    // 虽然这个类是空的，但依然可以运行JUnit测试， 运行时，它会将JUnitTestController.class 和 UserControllerTest.class
    // 中的所有测试用例都执行一遍

}
