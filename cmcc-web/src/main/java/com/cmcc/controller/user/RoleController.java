package com.cmcc.controller.user;

import com.cmcc.annotation.Log;
import com.cmcc.controller.base.BaseController;
import com.cmcc.enums.EnumErrCode;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.user.Role;
import com.cmcc.model.user.User;
import com.cmcc.service.user.RoleService;
import com.cmcc.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C), 2019-2019,
 * FileName: RoleController
 * Author:   陈腾帅
 * Date:     2019 5 13 0013 9:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
@RestController
@Slf4j
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleServiceImpl;

    /**
     * 查询用户角色
     * @param roleName 角色名
     * @param companyId 公司名
     * @return
     */
    @RequiresPermissions("role:query")
    @GetMapping("/query")
    public ResponseEntity queryRole(@RequestParam(value = "roleName", required = false) String roleName,
                                    @RequestParam(value = "companyId") Long companyId) {
        GeneralReturnMessage generalReturnMessage = roleServiceImpl.getALLRole(new Role().
                setRoleName(roleName).setDeleteFlag("N").setCompanyId(companyId));
        if (generalReturnMessage.isRequestFlag()) {
            return ResponseEntity.ok(generalReturnMessage.getData(), generalReturnMessage.getRetCode());
        } else {
            return ResponseEntity.fail("", "查询所有角色失败");
        }
    }

    /**
     * 分页查询角色列表
     *
     * @param roleName 角色名
     * @param companyId 公司名
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequiresPermissions("role:query")
    @GetMapping("/queryRoleByPage")
    public ResponseEntity queryRoleByPage(@RequestParam(value = "roleName", required = false) String roleName,
                                          @RequestParam(value = "companyId") Long companyId,
                                          @RequestParam(value = "pageNum") Integer pageNum,
                                          @RequestParam(value = "pageSize") Integer pageSize) {
        log.info("分页查询角色列表roleName:{},pageNum:{},pageSize:{}", roleName, pageNum, pageSize);
        try {
            if (null == pageNum || null == pageSize) {
                return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
            }
            GeneralReturnMessage generalReturnMessage = roleServiceImpl.queryRoleByPage(new Role().
                    setRoleName(roleName).setDeleteFlag("N").setCompanyId(companyId), pageNum, pageSize);
            if (!generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
            return ResponseEntity.ok(generalReturnMessage.getData(), generalReturnMessage.getPageCount());
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }

    /**
     * 新增角色
     *
     * @param roleName  角色名
     * @param remark    备注
     * @param company_id 公司id
     * @return
     */
    @RequiresPermissions("role:insert")
    @GetMapping("/insertRrole")
    public ResponseEntity insertRole(@RequestParam(value = "roleName") String roleName,
                                     @RequestParam(value = "remark", required = false) String remark,
                                     @RequestParam(value = "companyId", required = false) Long company_id) {
        Role role = new Role();
        try {
            User userCurrent = getCurrentUser();
            if (StringUtils.isBlank(roleName)) {
                ResponseEntity.fail(EnumErrCode.RoleNameIsBlank);
            }
            role.setRoleName(roleName);
            role.setRemark(remark);
            role.setCreator(userCurrent.getId());
            role.setModifier(userCurrent.getId());
            role.setDeleteFlag("N");
            role.setCreateTime(new Date());
            role.setCompanyId(company_id);
            role.setModifyTime(new Date());
            GeneralReturnMessage generalReturnMessage = roleServiceImpl.insertRole(role);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData(), generalReturnMessage.getRetCode());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            return ResponseEntity.fail("", e.toString());
        }
    }


    /**
     * 删除用户角色
     *
     * @param roleId
     * @param roleName
     * @return
     */
    @RequiresPermissions("role:delete")
    @GetMapping("/deleteRole")
    public ResponseEntity deleteRole(@RequestParam(value = "roleId", required = false) Long roleId,
                                     @RequestParam(value = "roleName", required = false) String roleName) {
        log.info("删除用户角色,角色id:{},角色名:{}", roleId, roleName);
        if (roleId == null && StringUtils.isBlank(roleName)) {
            return ResponseEntity.fail(EnumErrCode.RoleNameAndRoleIdIsBlank.getRetCode(), EnumErrCode.RoleNameAndRoleIdIsBlank.getRetInfo());
        }
        try {
            User userCurrent = super.getCurrentUser();
            GeneralReturnMessage generalReturnMessage = roleServiceImpl.deleteRole(new Role().setRoleId(roleId).setRoleName(roleName));
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData(), generalReturnMessage.getRetCode());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            return ResponseEntity.fail("", e.toString());
        }
    }

    /**
     * 修改用户角色
     *
     * @param roleId
     * @param roleName   角色名
     * @param companyId  公司名
     * @param remark     备注
     * @param deleteFlag 删除标志
     * @return
     */
    @RequiresPermissions("role:update")
    @GetMapping("/updateRole")
    public ResponseEntity updateRole(@RequestParam(value = "roleId", required = true) Long roleId,
                                     @RequestParam(value = "roleName", required = false) String roleName,
                                     @RequestParam(value = "companyId", required = false) Long companyId,
                                     @RequestParam(value = "remark", required = false) String remark,
                                     @RequestParam(value = "deleteFlag", required = false) String deleteFlag) {
        log.info("修改用户角色：角色id:{},角色名:{},公司id{},备注:{}", roleId, roleName, companyId, remark);
        if (roleId == null) {
            ResponseEntity.fail(EnumErrCode.RoleIdIsBlank.getRetCode(), EnumErrCode.RoleIdIsBlank.getRetInfo());
        }
        try {
            User userCurrent = super.getCurrentUser();
            Role role = new Role().setRoleId(roleId).setRoleName(roleName).setCompanyId(companyId).setRemark(remark)
                    .setModifier(userCurrent.getId()).setModifyTime(new Date()).setDeleteFlag(deleteFlag);
            GeneralReturnMessage generalReturnMessage = roleServiceImpl.updateRole(role);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData(), generalReturnMessage.getRetCode());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            return ResponseEntity.fail("", e.toString());
        }
    }

    /**
     * 修改角色的菜单权限
     *
     * @param roleId 角色id
     * @param menuIds 1,2,4
     * @return
     */
    @Log("修改角色的菜单权限")
    @PostMapping("/updateRoleMenu")
    @RequiresPermissions("role:updateRoleMenu")
    public ResponseEntity updateRoleMenu(@RequestParam(value = "roleId") Long roleId,
                                         @RequestParam(value = "menuIds") Long[] menuIds) {
        //当前操作角色不能为空
        if (roleId == null) {
            return ResponseEntity.fail(EnumErrCode.UserIdIsBlank);
        }
        //menu不能为空
        if (menuIds == null || menuIds.length == 0) {
            return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
        }
        try {
            //获得当前用户
            User currentUser = super.getCurrentUser();
            List<Long> currentMenuIdList = Arrays.asList(menuIds);
            GeneralReturnMessage generalReturnMessage = roleServiceImpl.updateRoleMenu(roleId, currentUser.getId(), currentMenuIdList);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            return ResponseEntity.fail("", e.toString());
        }
    }

    /**
     * 根据角色id查询角色菜单权限
     *
     * @param roleId
     * @return
     */
    @PostMapping("/queryRoleMenu")
    public ResponseEntity queryRoleMenu(@RequestParam(value = "roleId") Long roleId) {
        try {
            GeneralReturnMessage generalReturnMessage = roleServiceImpl.queryRoleMenu(roleId);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("根据角色id查询角色的菜单", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }
}
