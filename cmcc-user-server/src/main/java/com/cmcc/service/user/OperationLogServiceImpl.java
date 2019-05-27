package com.cmcc.service.user;


import com.cmcc.dao.user.OperationLogDao;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (C), 2019-2019,
 * FileName: OperationLogServiceImpl
 * Author:   陈腾帅
 * Date:     2019 5 9 0009 15:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    OperationLogDao operationLogDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertLog(OperationLog operationLog) throws Exception {
        operationLogDao.insert(operationLog);
    }

    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage queryPrimaryKey(long id) throws Exception {
        try {
            OperationLog operationLog = operationLogDao.selectByPrimaryKey(id);
            return GeneralReturnMessage.success(operationLog);
        } catch (Exception e) {
            return GeneralReturnMessage.fail(e.toString());
        }
    }
}
