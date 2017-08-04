package org.zh.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zh.bean.CustomUserDetails;
import org.zh.bean.Permission;
import org.zh.service.ISysApiService;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2017.1.5. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/8/2  <br/>
 * Time: 10:11  <br/>
 *
 * @Description: 描述本类的作用
 */
@ApiIgnore
@Controller
@RequestMapping(value = "/")
public class BasePageController {

    @Autowired
    private ISysApiService sysApiService;

    @RequestMapping(value = "/signin.html", method = RequestMethod.GET)
    public String signin() {
        return "/signin";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "测试接口")
    public String test(Model model) {
        return "/index";
    }

    @RequestMapping(value = {"login.html", "login"}, method = RequestMethod.GET)
    public String login() {
        return "/login";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String registerPage() {
        return "/register";
    }

    @RequestMapping(value = "left.html", method = RequestMethod.GET)
    public String left(Model model, HttpServletRequest request) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Permission> permissions = sysApiService.selectUserPermissionList(userDetails.getId());
            model.addAttribute("permissions", permissions);
        } catch (Exception e) {
            //重新登录时销毁该用户的Session
            request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
        }
        return "/left";
    }

}
