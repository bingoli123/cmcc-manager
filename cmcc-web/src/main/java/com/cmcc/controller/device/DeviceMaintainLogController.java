package com.cmcc.controller.device;

import com.cmcc.annotation.Log;
import com.cmcc.controller.base.BaseController;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.device.DeviceMaintainLog;
import com.cmcc.model.user.User;
import com.cmcc.service.device.DeviceMaintainLogService;
import com.cmcc.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (C), 2019-2019,
 * FileName: DeviceMaintainLogController
 * Author:   陈腾帅
 * Date:     2019 5 16 0016 14:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
@RestController
@Slf4j
@RequestMapping("/device")
public class DeviceMaintainLogController extends BaseController {

    @Autowired
    private DeviceMaintainLogService deviceMaintainLogServiceImpl;

    @Log("新增设备维修记录")
    @RequiresPermissions("device:insertDeviceMaintainLog")
    @PostMapping("/insertDeviceMaintainLog")
    public ResponseEntity insertDeviceMaintainLog(@RequestParam(value = "deviceId") Long deviceId,
                                                  @RequestParam(value = "maintainTime") String maintainTime,
                                                  @RequestParam(value = "maintainUser") String maintainUser,
                                                  @RequestParam(value = "remark", required = false) String remark) {
        DeviceMaintainLog deviceMaintainLog = new DeviceMaintainLog();
        try {
            User currentUser = super.getCurrentUser();
            if (StringUtils.isNotBlank(maintainTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                deviceMaintainLog.setMaintainTime(sdf.parse(maintainTime));
            }
            deviceMaintainLog.setDeviceId(deviceId).
                    setMaintainUser(maintainUser).setRemark(remark).setDeleteFlag("N").
                    setCreator(currentUser.getId()).setModifier(currentUser.getId()).
                    setCreateTime(new Date()).setModifyTime(new Date());
            GeneralReturnMessage generalReturnMessage = deviceMaintainLogServiceImpl.insert(deviceMaintainLog);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok("新增设备维修记录成功");
            } else {
                log.error("新增设备维修记录,{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("新增设备维修记录,{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }

    @Log("删除设备维修记录")
    @RequiresPermissions("device:deleteDeviceMaintainLog")
    @PostMapping("/deleteDeviceMaintainLog")
    public ResponseEntity deleteDeviceMaintainLog(@RequestParam(value = "id") Long id) {
        try {
            GeneralReturnMessage generalReturnMessage = deviceMaintainLogServiceImpl.deleteById(id);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok("删除设备维修记录成功");
            } else {
                log.error("删除设备维修记录,{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("删除设备维修记录,{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }

    @Log("修改设备维修记录")
    @RequiresPermissions("device:updateDeviceMaintainLog")
    @PostMapping("/updateDeviceMaintainLog")
    public ResponseEntity updateDeviceMaintainLog(@RequestParam(value = "id") Long id,
                                                  @RequestParam(value = "deviceId", required = false) Long deviceId,
                                                  @RequestParam(value = "maintainTime", required = false) String maintainTime,
                                                  @RequestParam(value = "maintainUser", required = false) String maintainUser,
                                                  @RequestParam(value = "remark", required = false) String remark) {
        DeviceMaintainLog deviceMaintainLog = new DeviceMaintainLog();
        try {
            User currentUser = super.getCurrentUser();
            if (StringUtils.isNotBlank(maintainTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                deviceMaintainLog.setMaintainTime(sdf.parse(maintainTime));
            }
            deviceMaintainLog.setId(id).setDeviceId(deviceId).setMaintainUser(maintainUser).
                    setRemark(remark).setModifier(currentUser.getId()).setModifyTime(new Date());
            GeneralReturnMessage generalReturnMessage = deviceMaintainLogServiceImpl.update(deviceMaintainLog);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok("修改设备维修记录成功");
            } else {
                log.error("修改设备维修记录,{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("修改设备维修记录,{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }

    @Log("分页查询设备维修记录")
    @RequiresPermissions("device:queryDeviceMaintainLog")
    @PostMapping("/queryDeviceMaintainLog")
    public ResponseEntity queryDeviceMaintainLog(@RequestParam(value = "id") Long id,
                                                 @RequestParam(value = "deviceId") Long deviceId,
                                                 @RequestParam(value = "maintainTime") String maintainTime,
                                                 @RequestParam(value = "maintainUser") String maintainUser,
                                                 @RequestParam(value = "remark") String remark,
                                                 @RequestParam(value = "pageNum") Integer pageNum,
                                                 @RequestParam(value = "pageSize") Integer pageSize) {
        DeviceMaintainLog deviceMaintainLog = new DeviceMaintainLog();
        try {
            User currentUser = super.getCurrentUser();
            if (StringUtils.isNotBlank(maintainTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                deviceMaintainLog.setMaintainTime(sdf.parse(maintainTime));
            }
            deviceMaintainLog.setId(id).setDeviceId(deviceId).
                    setMaintainUser(maintainUser).setRemark(remark).setDeleteFlag("N");
            GeneralReturnMessage generalReturnMessage = deviceMaintainLogServiceImpl.queryDeviceMaintainLogByPage(deviceMaintainLog, pageNum, pageSize);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                log.error("分页查询设备维修记录,{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("分页查询设备维修记录,{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }
}
