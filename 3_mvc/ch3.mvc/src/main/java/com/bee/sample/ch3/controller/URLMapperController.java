package com.bee.sample.ch3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bee.sample.ch3.entity.User;
import com.bee.sample.ch3.service.UserService;

/**
 * mvc url映射测试
 *
 * @author lijiazhi
 */
@Controller
@RequestMapping("/urlmapper")
public class URLMapperController {

    @Autowired
    private UserService userService;

    @Value("${query.all:2}")
    public Integer queryAll;

    /**
     * Ant路径表达式
     * Ant用符号 “*” 来表示匹配任意字符，
     * 用 “**” 来表示统配任意路径，
     * 用 “?” 来匹配单个字符
     * @return
     */
    @RequestMapping(path = "/user/all/*.json", method = RequestMethod.GET)
    @ResponseBody
    public List<User> allUser() {
        return userService.allUser();
    }

    @RequestMapping(path = "/user/{id}.json", method = RequestMethod.GET)
    @ResponseBody
    public User getById(@PathVariable(value = "id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(path = "/{userId}.json", produces = "application/json")
    @ResponseBody
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping(value = "/consumes/test.json", consumes = "application/json")
    @ResponseBody
    public User forJson() {
        return userService.getUserById(1L);
    }

    /**
     * 支持通过${}来获取系统的配置文件或者环境变量
     * @return
     */
    @RequestMapping("/${query.all}.json")
    @ResponseBody
    public List<User> all() {
        return userService.allUser();
    }

}
