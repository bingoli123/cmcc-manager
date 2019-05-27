package com.cmcc.service.cd;

import com.cmcc.dao.cd.CodelistDao;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.cd.Codelist;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (C), 2019-2019,
 * FileName: CodeListServiceImpl
 * Author:   陈腾帅
 * Date:     2019 5 13 0013 13:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
@Slf4j
@Service
public class CodelistServiceImpl implements CodelistService {

    @Autowired
    private CodelistDao codelistDao;

    /**
     * 查询指定数据
     *
     * @param codelist
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage selectCodeList(Codelist codelist) {
        try {
            List<Codelist> codelists = codelistDao.selectCodeList(codelist);
            return GeneralReturnMessage.success(codelists);
        } catch (Exception e) {
            log.error(e.toString());
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 分页查询
     *
     * @param codelist
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public GeneralReturnMessage selectCodeList(Codelist codelist, Integer pageNum, Integer pageSize) {
        try {
            log.info("分页查询CodeList");
            Page page = PageHelper.startPage(pageNum, pageSize);
            List<Codelist> codelists = codelistDao.selectCodeList(codelist);
            return GeneralReturnMessage.success(codelists, page.getTotal());
        } catch (Exception e) {
            log.error(e.toString());
            return GeneralReturnMessage.fail(e.toString());
        }
    }
}
