package com.cmcc.service.user;

import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.OperationLog;

/**
 * Copyright (C), 2019-2019,
 * FileName: OperationLogService
 * Author:   陈腾帅
 * Date:     2019 5 9 0009 15:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅           修改时间           V1.0              描述
 */
public interface OperationLogService {

    void insertLog(OperationLog operationLog) throws Exception;

    GeneralReturnMessage queryPrimaryKey(long id) throws Exception;
}
