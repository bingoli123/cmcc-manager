package com.cmcc.dao.company;

import com.cmcc.model.company.DataCompany;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公司信息表(DataCompany)表数据库访问层
 *
 * @author lidongbin
 * @since 2019-05-13 09:36:13
 */
@Mapper
@Repository
public interface DataCompanyDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DataCompany queryById(Long id);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param dataCompany 实例对象
     * @return 对象列表
     */
    List<DataCompany> queryAll(DataCompany dataCompany);

    /**
     * 新增数据
     *
     * @param dataCompany 实例对象
     * @return 影响行数
     */
    int insert(DataCompany dataCompany);

    /**
     * 修改数据
     *
     * @param dataCompany 实例对象
     * @return 影响行数
     */
    int update(DataCompany dataCompany);


}