package com.cmcc.model.company;

import com.cmcc.model.PageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.io.Serializable;

/**
 * 公司信息表(DataCompany)实体类
 *
 * @author lidongbin
 * @since 2019-05-13 09:36:11
 */
@Getter
@Setter
@Accessors(chain = true)
public class DataCompany extends PageUtil implements Serializable {
    private static final long serialVersionUID = 768705083179763103L;
    //公司id
    private Long id;
    //公司名称
    private String companyName;
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
//    用户模糊查询
    private String companyNameLike;



}