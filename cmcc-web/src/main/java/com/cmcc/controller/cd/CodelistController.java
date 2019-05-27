package com.cmcc.controller.cd;

import com.cmcc.controller.base.BaseController;
import com.cmcc.model.GeneralReturnMessage;
import com.cmcc.model.cd.Codelist;
import com.cmcc.service.cd.CodelistService;
import com.cmcc.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (C), 2019-2019,
 * FileName: CodelistController
 * Author:   陈腾帅
 * Date:     2019 5 13 0013 14:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
@Slf4j
@RestController
@RequestMapping("/codelist")
public class CodelistController extends BaseController {

    @Autowired
    private CodelistService codelistServiceImpl;

    /**
     * 查询codelist
     *
     * @param kindValue  类型编码
     * @param kindName   类型名称
     * @param codeValue  值编码
     * @param codeName   值名称
     * @param remark     备注
     * @param attribute1 属性1
     * @param attribute2 属性2
     * @param deleteFlag 删除标志
     * @return
     */
    @GetMapping("/queryCodelist")
    public ResponseEntity queryCodellist(@RequestParam(value = "kindValue", required = false) String kindValue,
                                         @RequestParam(value = "kindName", required = false) String kindName,
                                         @RequestParam(value = "codeValue", required = false) String codeValue,
                                         @RequestParam(value = "codeName", required = false) String codeName,
                                         @RequestParam(value = "remark", required = false) String remark,
                                         @RequestParam(value = "attribute1", required = false) String attribute1,
                                         @RequestParam(value = "attribute2", required = false) String attribute2,
                                         @RequestParam(value = "deleteFlag", required = false) String deleteFlag) {
        Codelist codelist = new Codelist();
        codelist.setKindvalue(kindValue);
        codelist.setKindname(kindName);
        codelist.setCodevalue(codeValue);
        codelist.setCodename(codeName);
        codelist.setAttribute1(attribute1);
        codelist.setRemark(remark);
        codelist.setAttribute2(attribute2);
        codelist.setDeleteFlag(deleteFlag);
        try {
            GeneralReturnMessage generalReturnMessage = codelistServiceImpl.selectCodeList(codelist);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                log.error("查询codelist出错:{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("查询codelist出错:{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }

    /**
     * 查询codelist
     *
     * @param kindValue  类型编码
     * @param kindName   类型名称
     * @param codeValue  值编码
     * @param codeName   值名称
     * @param remark     备注
     * @param attribute1 属性1
     * @param attribute2 属性2
     * @param deleteFlag 删除标志
     * @param pageNum    页码
     * @param pageSize   页大小
     * @return
     */
    @RequiresPermissions("codelist:query")
    @GetMapping("/queryCodelistByPage")
    public ResponseEntity queryCodelistByPage(@RequestParam(value = "kindValue", required = false) String kindValue,
                                              @RequestParam(value = "kindName", required = false) String kindName,
                                              @RequestParam(value = "codeValue", required = false) String codeValue,
                                              @RequestParam(value = "codeName", required = false) String codeName,
                                              @RequestParam(value = "remark", required = false) String remark,
                                              @RequestParam(value = "attribute1", required = false) String attribute1,
                                              @RequestParam(value = "attribute2", required = false) String attribute2,
                                              @RequestParam(value = "deleteFlag", required = false) String deleteFlag,
                                              @RequestParam(value = "pageNum") Integer pageNum,
                                              @RequestParam(value = "pageSize") Integer pageSize) {
        Codelist codelist = new Codelist();
        codelist.setKindvalue(kindValue);
        codelist.setKindname(kindName);
        codelist.setCodevalue(codeValue);
        codelist.setCodename(codeName);
        codelist.setAttribute1(attribute1);
        codelist.setRemark(remark);
        codelist.setAttribute2(attribute2);
        codelist.setDeleteFlag(deleteFlag);
        try {
            GeneralReturnMessage generalReturnMessage = codelistServiceImpl.selectCodeList(codelist, pageNum, pageSize);
            if (generalReturnMessage.isRequestFlag()) {
                return ResponseEntity.ok(generalReturnMessage.getData());
            } else {
                log.error("查询codelist出错:{}", generalReturnMessage.getRequestMsg());
                return ResponseEntity.fail(generalReturnMessage.getRetCode(), generalReturnMessage.getRequestMsg());
            }
        } catch (Exception e) {
            log.error("查询codelist出错:{}", e.toString());
            return ResponseEntity.fail("", e.toString());
        }
    }
}
