package com.cmcc.model.project;

import java.util.Date;
import java.io.Serializable;

import com.cmcc.model.PageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
/**
 * 项目信息(DataProject)实体类
 *
 * @author lidongbin
 * @since 2019-05-15 09:38:08
 */
@Getter
@Setter
@Accessors(chain = true)
public class DataProject implements Serializable {
    private static final long serialVersionUID = 689881082102816543L;
    
    private Long id;
    //项目名称
    private String projectName;
    //项目地址
    private String projectAddress;
    //项目联系人名称
    private String projectContactName;
    //项目联系人电话
    private String projectContactPhone;
    //项目联系人邮箱
    private String projectContactEmail;
    //经销商名称
    private String distributorContactName;
    //经销商电话
    private String distributorContactPhone;
    //经销商邮箱
    private String distributorContactEmail;
    //省id 关联area_relation表
    private Long province;
    //市id 关联area_relation表
    private Long city;
    //建筑类型
    private String buildType;
    //热源形式
    private String warmSourceForm;
    //冷源形式
    private String coldSourceForm;
    //开始运行时间
    private Object startTime;
    //删除标识  N未删除 Y已删除
    private String deleteFlag;
    //创建时间
    private Date createTime;
    //创建人
    private Long creator;
    //修改时间
    private Date modifyTime;
    //修改人
    private Long modifier;

    private String creatorName;
    private String modifierName;

//    -----

    private String provinceName;
    private String cityName;



}