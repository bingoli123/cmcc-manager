package com.cmcc.service.company;

import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.company.DataCompany;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公司信息表(DataCompany)表服务接口
 *
 * @author lidongbin
 * @since 2019-05-13 09:36:14
 */
@Service
public interface DataCompanyService {

    /**
     * 查询公司信息列表 无分页
     * @param param
     * @return
     */
    GeneralReturnMessage queryAllCompany(DataCompany param);
    /**
     * 查询公司信息列表 分页
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    GeneralReturnMessage queryCompanyByPage(DataCompany param,int pageNum,int pageSize);
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GeneralReturnMessage queryById(Long id);


    /**
     * 新增数据
     *
     * @param dataCompany 实例对象
     * @return 实例对象
     */
    GeneralReturnMessage insert(DataCompany dataCompany);

    /**
     * 修改数据
     *
     * @param dataCompany 实例对象
     * @return 实例对象
     */
    GeneralReturnMessage update(DataCompany dataCompany);


}