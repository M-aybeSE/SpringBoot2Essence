package com.bee.sample.ch3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author linhx
 * @date 2022-10-17 18:05
 */
@Controller
@RequestMapping("/redirectAndForward")
public class RedirectAndForwardController {

    @RequestMapping("/order/saveorder.html")
    public String saveOrder(Integer orderId) {
        // 方法1
//        ModelAndView modelAndView = new ModelAndView("redirect:/order/detail.html?orderId=" + orderId);

        // 方法2
//        RedirectView redirectView = new RedirectView("/order/detail.html?orderId=" + orderId);

        // 方法3
        return "redirect:/order/detail.html?orderId=" + orderId;
    }

    /**
     * forward用来在controller执行完毕后，再执行另一个controller的方法
     * @return
     */
    @RequestMapping("/bbs")
    public String index() {
        return "forward:/bbs/module/1-1.html";
    }

    @RequestMapping("/bbs/module/{type}-{page}")
    public ModelAndView module(@PathVariable int type, @PathVariable int page) {
        return new ModelAndView();
    }
}
