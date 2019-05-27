package com.cmcc.controller.project;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ImageUtil;
import com.cmcc.annotation.Log;
import com.cmcc.controller.base.BaseController;
import com.cmcc.enums.EnumErrCode;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.project.DataProject;
import com.cmcc.model.user.User;
import com.cmcc.service.project.DataProjectService;
import com.cmcc.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;

/**
 * 项目信息(DataProject)表控制层
 *
 * @author lidongbin
 * @since 2019-05-15 09:38:12
 */
@RestController
@RequestMapping("dataProject")
@Slf4j
public class DataProjectController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private DataProjectService dataProjectServiceImpl;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public DataProject selectOne(Long id) {
        return dataProjectServiceImpl.queryById(id);
    }


//    增删改查  查询用户下边关联的所有项目  新增项目的时候自动增加关系表数据  查询本公司下的所有用户  查询项目列表（下拉框使用 无权限）


    /**
     * 查询某个用户下的所有的项目列表(下拉列表会用到)
     * @param projectName
     * @return
     */
    @PostMapping("queryProjectByUser")
    public ResponseEntity queryCompanyList(@RequestParam(value = "projectName",required = false) String projectName){
        try{
            log.info("开始查询全部项目列表无分页");
            User user = getCurrentUser();
            GeneralReturnMessage generalReturnMessage = dataProjectServiceImpl.queryProjectByUser(new DataProject().setProjectName(projectName),
                    user.getId());
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
            return ResponseEntity.ok((List<DataProject>)generalReturnMessage.getData());
        }catch(Exception e){
            log.error(e.toString());
            return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }
    /**
     * 分页查询项目列表
     * @param projectName 模糊查询名称
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Log("分页查询项目列表")
    @PostMapping("queryProjectByUserIdPage")
    @RequiresPermissions("project:list")
    public ResponseEntity queryCompanyByPage(@RequestParam(value = "projectName",required = false) String projectName,
                                             @RequestParam(value = "pageNum") Integer pageNum,
                                             @RequestParam(value = "pageSize") Integer pageSize){
        log.info("开始分页查询项目列表projectName:{},pageNum:{},pageSize:{}",projectName,pageNum,pageSize);
        try{
            if (null==pageNum||null==pageSize){
                return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
            }
            User user = getCurrentUser();
            GeneralReturnMessage generalReturnMessage = dataProjectServiceImpl.queryProjectByUserPage(
                    new DataProject().setProjectName(projectName),
                    user.getId(),
                    pageNum,
                    pageSize
                    );
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
            return ResponseEntity.ok((List<DataProject>)generalReturnMessage.getData(),generalReturnMessage.getPageCount());
        }catch(Exception e){
            log.error(e.toString());
            return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }

    /**
     * 保存项目
     * @param dataProject
     * @return
     */
    @Log("新增项目")
    @PostMapping("insertProject")
    @RequiresPermissions("project:insert")
    public ResponseEntity insertProject(@RequestBody DataProject dataProject){
        try{
            if (null==dataProject||StringUtils.isEmpty(dataProject.getProjectName())){
                return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
            }
            User user = getCurrentUser();
            dataProject.setCreator(user.getId()).setCreatorName(user.getUserName());
            GeneralReturnMessage generalReturnMessage = dataProjectServiceImpl.insert(dataProject);
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
            return ResponseEntity.ok(generalReturnMessage.getData());
        }catch(Exception e){
          log.error(e.toString());
          return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }

    /**
     * 修改项目
     * @param dataProject
     * @return
     */
    @Log("修改项目信息")
    @PostMapping("updateProject")
    @RequiresPermissions("project:update")
    public ResponseEntity updateProject(@RequestBody DataProject dataProject){
        try{
            if (null==dataProject.getId()){
                return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
            }
            User user = getCurrentUser();
            GeneralReturnMessage generalReturnMessage = dataProjectServiceImpl.update(
                    dataProject
                            .setModifyTime(DateUtil.date())
                            .setModifier(user.getId())
                            .setModifierName(user.getUserName())
            );
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
            return ResponseEntity.ok(generalReturnMessage.getRequestMsg());
        }catch(Exception e){
            log.error(e.toString());
            return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }

    /**
     * 删除项目信息
     * @param projectId
     * @return
     */
    @Log("删除项目")
    @PostMapping("deleteProject")
    @RequiresPermissions("project:delete")
    public ResponseEntity deleteProject(@RequestParam("projectId") Long projectId){
        try{
            if (null==projectId){
                return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
            }
            User user = getCurrentUser();
//            调用修改项目方法 修改 删除状态 修改时间 修改人
            GeneralReturnMessage generalReturnMessage = dataProjectServiceImpl.update(
                    new DataProject()
                            .setId(projectId)
                            .setDeleteFlag("Y")
                            .setModifyTime(DateUtil.date())
                            .setModifier(user.getId())
                            .setModifierName(user.getUserName())
            );
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
            return ResponseEntity.ok("删除成功");
        }catch(Exception e){
            log.error(e.toString());
            return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }

    /**
     * 项目授权给用户
     * @param projectId 项目id
     * @param chooseUserId 新选中的用户ID
     * @return
     */
    @Log("项目授权给用户")
    @PostMapping("projectAuthUsers")
    @RequiresPermissions("project:authuser")
    public ResponseEntity projectAuthUser(
            @RequestParam(value = "projectId") Long projectId,
            @RequestParam(value = "chooseUserId",required = false) Long [] chooseUserId
    ){
        try{
            if (null==projectId){
                return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
            }
            User user = getCurrentUser();
            GeneralReturnMessage generalReturnMessage = dataProjectServiceImpl.projectAuthUser(projectId,chooseUserId,user.getId());
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
        }catch(Exception e){
          log.error(e.toString());
          return ResponseEntity.fail(EnumErrCode.CommonError);
        }

        return null;
    }




}