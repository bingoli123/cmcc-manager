package com.cmcc.controller.company;

import cn.hutool.core.date.DateUtil;
import com.cmcc.annotation.Log;
import com.cmcc.controller.base.BaseController;
import com.cmcc.enums.EnumErrCode;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.company.DataCompany;
import com.cmcc.model.user.User;
import com.cmcc.service.company.DataCompanyService;
import com.cmcc.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 公司信息表(DataCompany)表控制层
 *
 * @author lidongbin
 * @since 2019-05-13 09:36:16
 */
@RestController
@RequestMapping("dataCompany")
@Slf4j
public class DataCompanyController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private DataCompanyService dataCompanyServiceImpl;


    @PostMapping("queryAllCompany")
    public ResponseEntity queryCompanyList(@RequestParam(value = "companyName",required = false) String companyName){
        try{
            log.info("开始查询全部公司列表无分页");
            GeneralReturnMessage generalReturnMessage = dataCompanyServiceImpl.queryAllCompany(new DataCompany().setCompanyNameLike(companyName));
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
            return ResponseEntity.ok((List<DataCompany>)generalReturnMessage.getData());
        }catch(Exception e){
          log.error(e.toString());
          return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }
    /**
     * 分页查询公司列表
     * @param companyName 模糊查询名称
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Log("分页查询公司列表")
    @PostMapping("queryCompanyByPage")
    @RequiresPermissions("company:list")
    public ResponseEntity queryCompanyByPage(@RequestParam(value = "companyName",required = false) String companyName,
                                                          @RequestParam(value = "pageNum") Integer pageNum,
                                                          @RequestParam(value = "pageSize") Integer pageSize){
        log.info("开始分页查询公司列表companyName:{},pageNum:{},pageSize:{}",companyName,pageNum,pageSize);
        try{
            if (null==pageNum||null==pageSize){
                return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
            }
            GeneralReturnMessage generalReturnMessage = dataCompanyServiceImpl.queryCompanyByPage(new DataCompany().setCompanyNameLike(companyName),pageNum,pageSize);
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
            return ResponseEntity.ok((List<DataCompany>)generalReturnMessage.getData(),generalReturnMessage.getPageCount());
        }catch(Exception e){
          log.error(e.toString());
          return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }
    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @PostMapping("selectOne")
    public ResponseEntity selectOne(@RequestParam("companyId") Long companyId) {
        return ResponseEntity.ok(dataCompanyServiceImpl.queryById(companyId));
    }

    /**
     * @desc 新增公司信息
     * @param companyName
     * @return
     */
    @Log("新增公司信息")
    @PostMapping("insertCompany")
    @RequiresPermissions("company:insert")
    public ResponseEntity insertCompany(@RequestParam(value = "companyName") String companyName){
        log.info("开始执行新增公司信息companyName:{}",companyName);
        try{
            User user = getCurrentUser();
            if (StringUtils.isBlank(companyName)){
                ResponseEntity.fail(EnumErrCode.COMPANY_NAME_NOT_NULL);
            }
            DataCompany dataCompany = new DataCompany()
                    .setCompanyName(companyName)
                    .setCreateTime(DateUtil.date())
                    .setCreator(user.getId());
            GeneralReturnMessage generalReturnMessage = dataCompanyServiceImpl.insert(dataCompany);
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
            return  ResponseEntity.ok(generalReturnMessage.getData());
        }catch(Exception e){
          log.error(e.toString());
          return ResponseEntity.fail(EnumErrCode.CommonError);
        }
    }

    /**
     * 删除公司信息
     * @param companyId
     * @return
     */
    @Log("删除公司信息")
    @PostMapping("deleteCompany")
    @RequiresPermissions("company:delete")
    public ResponseEntity deleteCompany(@RequestParam("companyId") Long companyId){
        log.info("开始执行删除公司列表companyId：{}",companyId);
        try{
            if (null==companyId){
                return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
            }
            User user = getCurrentUser();
            DataCompany dataCompany = new DataCompany()
                    .setId(companyId)
                    .setDeleteFlag("Y")
                    .setModifyTime(new Date())
                    .setModifier(user.getId());
            GeneralReturnMessage generalReturnMessage = dataCompanyServiceImpl.update(dataCompany);
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
     * 修改公司信息
     * @param companyId
     * @param companyName
     * @return
     */
    @Log("修改公司信息")
    @PostMapping("updateCompany")
    @RequiresPermissions("company:update")
    public ResponseEntity updateCompany(@RequestParam("companyId") Long companyId,
                                        @RequestParam("companyName")String companyName ){
        log.info("开始执行修改公司信息companyName:{},companyId:{}",companyName,companyId);
        try{
            if (null==companyId){
                return ResponseEntity.fail(EnumErrCode.SOME_PARAM_IS_NULL);
            }
            User user = getCurrentUser();
            DataCompany dataCompany = new DataCompany()
                    .setId(companyId)
                    .setCompanyName(companyName)
                    .setModifier(user.getId())
                    .setModifyTime(DateUtil.date());
            GeneralReturnMessage generalReturnMessage = dataCompanyServiceImpl.update(dataCompany);
            if (!generalReturnMessage.isRequestFlag()){
                return ResponseEntity.fail(generalReturnMessage.getRetCode(),generalReturnMessage.getRequestMsg());
            }
            return ResponseEntity.ok(generalReturnMessage.getRequestMsg());
        }catch(Exception e){
          log.error(e.toString());
          return ResponseEntity.fail(EnumErrCode.CommonError);
        }

    }

}