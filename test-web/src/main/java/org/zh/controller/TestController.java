package org.zh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 测试Controller
 * Created by ZhaoHang on 2017/7/25.
 */
@Controller
@Api(value = "测试", description = "测试")
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "测试接口")
    public String test(Model model) {
        return "views/index";
    }

}
