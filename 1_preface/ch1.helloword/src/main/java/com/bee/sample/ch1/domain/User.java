package com.bee.sample.ch1.domain;

/**
 * @author linhx
 * @date 2022-10-25 10:54
 */
public class User {

    private Integer age;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
