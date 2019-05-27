package com.cmcc.service.device;

import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.device.DeviceMaintainLog;

/**
 * Copyright (C), 2019-2019,
 * Author:   陈腾帅
 * Date:     2019-05-16 14:03:05
 * Description:维修记录表(DeviceMaintainLog)表数据库sevice层
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          $time.currTime          V1.0              描述
 */
public interface DeviceMaintainLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GeneralReturnMessage queryById(Long id);

    /**
     * 分页查询
     *
     * @param deviceMaintainLog
     * @param pageNum           页码
     * @param pageSize          页大小
     * @return
     */
    GeneralReturnMessage queryDeviceMaintainLogByPage(DeviceMaintainLog deviceMaintainLog, Integer pageNum, Integer pageSize);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    GeneralReturnMessage queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param deviceMaintainLog 实例对象
     * @return 实例对象
     */
    GeneralReturnMessage insert(DeviceMaintainLog deviceMaintainLog) throws Exception;

    /**
     * 修改数据
     *
     * @param deviceMaintainLog 实例对象
     * @return 实例对象
     */
    GeneralReturnMessage update(DeviceMaintainLog deviceMaintainLog) throws Exception;

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    GeneralReturnMessage deleteById(Long id) throws Exception;

}