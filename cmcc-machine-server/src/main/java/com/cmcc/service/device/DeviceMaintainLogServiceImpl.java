package com.cmcc.service.device;

import com.cmcc.dao.device.DeviceMaintainLogDao;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.device.DeviceMaintainLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (C), 2019-2019,
 * Author:   陈腾帅
 * Date:     2019-05-16 14:08:35
 * Description: 维修记录表(DeviceMaintainLog)表服务实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          2019-05-16 14:08:35         V1.0              描述
 */
@Slf4j
@Service
public class DeviceMaintainLogServiceImpl implements DeviceMaintainLogService {
    @Autowired
    private DeviceMaintainLogDao deviceMaintainLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage queryById(Long id) {
        try {
            DeviceMaintainLog deviceMaintainLog = this.deviceMaintainLogDao.queryById(id);
            return GeneralReturnMessage.success(deviceMaintainLog);
        } catch (Exception e) {
            log.error("查询数据失败,{}", e.toString());
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 分页查询
     *
     * @param deviceMaintainLog
     * @param pageNum           页码
     * @param pageSize          页大小
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage queryDeviceMaintainLogByPage(DeviceMaintainLog deviceMaintainLog, Integer pageNum, Integer pageSize) {
        log.info("分页查询设备维护列表");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<DeviceMaintainLog> list = deviceMaintainLogDao.queryAll(deviceMaintainLog);
        return GeneralReturnMessage.success(list, page.getTotal());
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Transactional(readOnly = true)
    @Override
    public GeneralReturnMessage queryAllByLimit(int offset, int limit) {
        try {
            List<DeviceMaintainLog> deviceMaintainLogList = this.deviceMaintainLogDao.queryAllByLimit(offset, limit);
            return GeneralReturnMessage.success(deviceMaintainLogList);
        } catch (Exception e) {
            log.error("查询数据失败,{}", e.toString());
            return GeneralReturnMessage.fail(e.toString());
        }
    }

    /**
     * 新增数据
     *
     * @param deviceMaintainLog 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage insert(DeviceMaintainLog deviceMaintainLog) throws Exception {
        //TODO 判断deviceid是否存在
        int i = deviceMaintainLogDao.insert(deviceMaintainLog);
        if (i > 0) {
            return GeneralReturnMessage.success("插入成功");
        } else {
            throw new Exception("插入数据失败");
        }
    }

    /**
     * 修改数据
     *
     * @param deviceMaintainLog 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage update(DeviceMaintainLog deviceMaintainLog) throws Exception {
        //TODO 判断deviceid是否存在
        int i = this.deviceMaintainLogDao.update(deviceMaintainLog);
        if (i > 0) {
            return GeneralReturnMessage.success("修改成功");
        } else {
            throw new Exception("修改数据失败");
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralReturnMessage deleteById(Long id) throws Exception {
        //TODO 判断id是否存在
        int i = this.deviceMaintainLogDao.deleteById(id);
        if (i > 0) {
            return GeneralReturnMessage.success("删除成功");
        } else {
            throw new Exception("删除数据失败");
        }
    }
}