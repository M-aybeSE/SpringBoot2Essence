package com.bee.sample.ch3.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bee.sample.ch3.entity.User;
import com.bee.sample.ch3.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * url映射到方法
 *
 * @author xiandafu
 */
@Controller
@RequestMapping("/user4")
public class Sample34Controller {

    @Autowired
    UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/")
    public @ResponseBody String index() {
        return "hell";
    }

    /**
     * 客户端请求必须包含application/json 才会处理
     * @return
     */
    @GetMapping(value = "/all1.json", consumes = "application/json")
    @ResponseBody
    public User forJson() {
        return userService.getUserById(1L);
    }

    @GetMapping(path = "/user/{userId}.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public User getUser(@PathVariable Long userId, Model model) {
        return userService.getUserById(userId);
    }

    @GetMapping(path = "/update.json", params = "action=save")
    @ResponseBody
    public void saveUser() {
        System.out.println("call save");
    }

    @GetMapping(path = "/update.json", params = "action=update")
    @ResponseBody
    public void updateUser() {
        System.out.println("call update");
    }

    @GetMapping(path = "/header.json", headers = "action=checkHeader")
    @ResponseBody
    public void checkHeader() {
        System.out.println("check header");
    }

    @ResponseBody
    @GetMapping(value = "/show/httpServletRequest")
    public Map<String, String> showHttpServletRequest(HttpServletRequest request) throws JsonProcessingException {
        Map httpInfo = new HashMap<>();

/*        ObjectMapper objectMapper = new ObjectMapper();
        // 忽略空值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

//        String httpServletRequestStr = JSON.toJSONString(httpServletRequest);
        httpInfo.put("httpServletRequest", objectMapper.writeValueAsString(httpServletRequest));

//        String requestStr = JSON.toJSONString(request);
        httpInfo.put("requestStr", objectMapper.writeValueAsString(request));*/

        // 打印出头部
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("headerNames===" + headerNames);
        while(headerNames.hasMoreElements()) {//判断是否还有下一个元素
            String nextElement = headerNames.nextElement();//获取headerNames集合中的请求头
            String header2 = request.getHeader(nextElement);//通过请求头得到请求内容
            System.out.println("请求头=========={}" + nextElement + "VALUE:" + header2);
            httpInfo.put(nextElement, header2);
            //System.out.println(nextElement+":"+header2);
        }

        String header = request.getHeader("host");
        System.out.println("header" + header);

        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("parameterMap:" + JSON.toJSONString(parameterMap));

        Map attributeMap = new HashMap();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String nextElement = attributeNames.nextElement();
            Object attribute = request.getAttribute(nextElement);
            attributeMap.put(nextElement, attribute);
        }
//        httpInfo.put("attributeMap", )


        return httpInfo;
    }
}
