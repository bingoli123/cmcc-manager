package com.cmcc.config.shiro.handler;

/**
 * Copyright (C), 2019-2019,
 * FileName: GlobalExceptionHandler
 * Author:   陈腾帅
 * Date:     2019 5 6 0006 17:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */

import com.cmcc.utils.ResponseEntity;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    public Object handleAuthorizationException() {
        return ResponseEntity.fail("2222222", "暂无权限，请联系管理员！");
    }

    @ExceptionHandler(value = ExpiredSessionException.class)
    public Object handleExpiredSessionException() {
        return ResponseEntity.fail("2222222", "还没有登陆，请重新登录！");
    }
}
