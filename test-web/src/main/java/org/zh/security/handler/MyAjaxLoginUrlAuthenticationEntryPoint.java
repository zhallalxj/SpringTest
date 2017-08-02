/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.zh.security.handler;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Description:TODO(未认证的情况下 Ajax 请求格式返回 )
 * @author: level.meng
 * @date: 2017年2月16日 下午4:55:13
 * <p>
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MyAjaxLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @SuppressWarnings("static-access")
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // TODO Auto-generated method stub

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        response.getWriter().write("未登录，请登录后操作！");
        response.getWriter().close();

    }


}
