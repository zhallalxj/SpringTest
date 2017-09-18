package org.zh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zh.auth.Digests;
import org.zh.auth.Encodes;
import org.zh.bean.User;
import org.zh.bean.UserExample;
import org.zh.service.IUserService;
import org.zh.utils.BCryptUtil;
import org.zh.utils.CustomResponse;
import org.zh.utils.DataTableJSONResponse;
import org.zh.utils.PageRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2017.1.5. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/8/3  <br/>
 * Time: 17:03  <br/>
 *
 * @Description: 描述本类的作用
 */
@ApiIgnore
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private DataTableJSONResponse dataTableJSONResponse;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public String userPage() {
        return "/user/user";
    }

    @RequestMapping(value = "/addUser.html", method = RequestMethod.GET)
    public String addUser() {
        return "/user/addUser";
    }

    @ResponseBody
    @RequestMapping(value = "/listAjax", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String listAjax(HttpServletRequest request) {
        PageRequest pageRequest = new PageRequest(request);

        UserExample userExample = new UserExample();
        userExample.setLimit(pageRequest.getLimit());
        userExample.setOffset(pageRequest.getOffset());
        userExample.setOrderByClause(pageRequest.getOrderByClause());
        List<User> users = userService.selectByExample(userExample);
        long count = userService.countByExample(userExample);


        return dataTableJSONResponse.toJSONString(pageRequest.getDraw(), count, count, users);
    }

    @ResponseBody
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String addUser(User user) {
        CustomResponse customResponse = new CustomResponse();

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(user.getUserName());

        User user1 = userService.selectFirstByExample(userExample);

        if(user1 != null) return customResponse.getErrorJson("用户名重复");

        byte[] salt = Digests.generateSalt(8);
        user.setSalt(Encodes.encodeHex(salt));

        user.setPassword(BCryptUtil.BCryptEncode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int result = userService.insertSelective(user);
        if (result == 0) return customResponse.getErrorJson("添加失败");

        return customResponse.getSuccessJson("添加成功");
    }

}
