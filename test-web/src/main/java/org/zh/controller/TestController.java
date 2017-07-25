package org.zh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试Controller
 * Created by ZhaoHang on 2017/7/25.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping
    public String test(Model model) {
        return "views/index";
    }

}
