package com.cmcc.config.shiro;

/**
 * Copyright (C), 2019-2019,
 * FileName: CustomUserFilter
 * Author:   陈腾帅
 * Date:     2019 5 6 0006 17:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */

import com.alibaba.fastjson.JSON;
import com.cmcc.utils.ResponseEntity;
import com.cmcc.enums.EnumErrCode;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class CustomUserFilter extends UserFilter {

    /**
     *
     * 如果是，则返回 403 状态码
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            httpServletResponse.setContentType("application/json; charset=utf-8");
            httpServletResponse.getWriter().print(JSON.toJSONString(ResponseEntity.fail(EnumErrCode.AuthenticationFailed.getRetCode(), EnumErrCode.AuthenticationFailed.getRetInfo())));
            return false;
    }
}