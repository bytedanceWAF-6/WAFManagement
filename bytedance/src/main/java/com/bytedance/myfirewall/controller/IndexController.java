package com.bytedance.myfirewall.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 登录之后首页跳转
 */

@Controller
public class IndexController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {

        return "index";
    }


}
