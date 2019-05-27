package com.cmcc.model.cd;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * cd_codelist
 *
 * @author
 */
@Setter
@Getter
@Accessors(chain = true)
public class Codelist implements Serializable {
    /**
     * primary key
     */
    private Long rowId;

    /**
     * 类型值
     */
    private String kindvalue;

    /**
     * 类型名称
     */
    private String kindname;

    /**
     * CODEVALUE
     */
    private String codevalue;

    /**
     * CODENAME
     */
    private String codename;

    /**
     * 排序
     */
    private String sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 预留字段1
     */
    private String attribute1;

    /**
     * 预留字段2
     */
    private String attribute2;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Long modifier;

    /**
     * 删除标识  N未删除 Y已删除
     */
    private String deleteFlag;

    private static final long serialVersionUID = 1L;

}