package com.cmcc.service.company;

import com.cmcc.enums.EnumErrCode;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.company.DataCompany;
import com.cmcc.dao.company.DataCompanyDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

/**
 * 公司信息表(DataCompany)表服务实现类
 *
 * @author lidongbin
 * @since 2019-05-13 09:36:15
 */
@Service
@Slf4j
public class DataCompanyServiceImpl implements DataCompanyService {
    @Autowired
    private DataCompanyDao dataCompanyDao;


    /**
     * 查询公司信息列表 无分页
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public  GeneralReturnMessage queryAllCompany(DataCompany param){
        log.info("开始查询所有的公司列表，无分页");
        param.setDeleteFlag("N");
        List<DataCompany> list = dataCompanyDao.queryAll(param);
        return GeneralReturnMessage.success(list);
    }

    @Override
    @Transactional(readOnly = true)
    public GeneralReturnMessage queryCompanyByPage(DataCompany param,int pageNum,int pageSize){
        log.info("分页查询公司列表");
        Page page = PageHelper.startPage(pageNum,pageSize);
        param.setDeleteFlag("N");
        List<DataCompany> list = dataCompanyDao.queryAll(param);
        return GeneralReturnMessage.success(list,page.getTotal());
    }


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    @Transactional(readOnly = true)
    public GeneralReturnMessage queryById(Long id) {
        return GeneralReturnMessage.success(dataCompanyDao.queryById(id));
    }



    /**
     * 新增数据
     *
     * @param dataCompany 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralReturnMessage insert(DataCompany dataCompany) {
        log.info("保存公司信息");
        if (StringUtils.isNotBlank(dataCompany.getCompanyName())){
            List<DataCompany> list = dataCompanyDao.queryAll(new DataCompany().setCompanyName(dataCompany.getCompanyName()));
            if (list!=null&&list.size()>0){
                return GeneralReturnMessage.fail(EnumErrCode.COMPANY_HAS_SAMENAME);
            }
        }
        dataCompany.setDeleteFlag("N").setCreateTime(new Date());
        int a = dataCompanyDao.insert(dataCompany);
        if (a!=1){
            return GeneralReturnMessage.fail(EnumErrCode.COMPANY_INSERT_FAIL);
        }else {
            return GeneralReturnMessage.success(dataCompany);
        }
    }

    /**
     * 修改数据
     *
     * @param dataCompany 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralReturnMessage update(DataCompany dataCompany) {
        log.info("修改公司信息");
        if (null==dataCompany.getId()){
            return GeneralReturnMessage.fail(EnumErrCode.COMPANY_UPDATE_FAIL);
        }
        if (StringUtils.isNotBlank(dataCompany.getCompanyName())){
            List<DataCompany> list = dataCompanyDao.queryAll(new DataCompany().setCompanyName(dataCompany.getCompanyName()));
            if (list!=null&&list.size()>0){
                return GeneralReturnMessage.fail(EnumErrCode.COMPANY_HAS_SAMENAME);
            }
        }
        int a = dataCompanyDao.update(dataCompany);
        return GeneralReturnMessage.success(EnumErrCode.COMPANY_UPDATE_SUCCESS.getRetInfo());
    }



}