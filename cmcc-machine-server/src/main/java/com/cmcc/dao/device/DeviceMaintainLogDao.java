package com.cmcc.dao.device;


import com.cmcc.model.device.DeviceMaintainLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
 /**
 * Copyright (C), 2019-2019,
 * Author:   陈腾帅
 * Date:     2019-05-16 14:05:52
 * Description: 维修记录表(DeviceMaintainLog)表数据库访问层
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          2019-05-16 14:05:52         V1.0              描述
 */
@Mapper
@Repository
public interface DeviceMaintainLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DeviceMaintainLog queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<DeviceMaintainLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param deviceMaintainLog 实例对象
     * @return 对象列表
     */
    List<DeviceMaintainLog> queryAll(DeviceMaintainLog deviceMaintainLog);

    /**
     * 新增数据
     *
     * @param deviceMaintainLog 实例对象
     * @return 影响行数
     */
    int insert(DeviceMaintainLog deviceMaintainLog);
    /**
     * 新增数据
     *
     * @param deviceMaintainLog 实例对象
     * @return 影响行数
     */
    int insertSelective(DeviceMaintainLog deviceMaintainLog);
    /**
     * 修改数据
     *
     * @param deviceMaintainLog 实例对象
     * @return 影响行数
     */
    int update(DeviceMaintainLog deviceMaintainLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}