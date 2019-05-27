package com.cmcc.config.shiro;

/**
 * Copyright (C), 2019-2019,
 * FileName: ShiroSessionListener
 * Author:   陈腾帅
 * Date:     2019 5 6 0006 17:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

public class ShiroSessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();

    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }
}
