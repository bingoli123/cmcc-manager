package com.cmcc.service.cd;

import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.cd.Codelist;

/**
 * Copyright (C), 2019-2019,
 * FileName: CodelistService
 * Author:   陈腾帅
 * Date:     2019 5 13 0013 13:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
public interface CodelistService {

    /**
     * 查询指定数据
     *
     * @param codelist
     * @return
     */
    GeneralReturnMessage selectCodeList(Codelist codelist);

    GeneralReturnMessage selectCodeList(Codelist codelist, Integer pageNum, Integer pageSize);

}
