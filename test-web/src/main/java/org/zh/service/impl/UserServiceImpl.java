package org.zh.service.impl;

import org.springframework.stereotype.Service;
import org.zh.base.BaseServiceImpl;
import org.zh.bean.User;
import org.zh.bean.UserExample;
import org.zh.dao.UserMapper;
import org.zh.service.IUserService;

/**
 * Created by ZhaoHang on 2017/7/25.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, UserExample> implements IUserService {
}
