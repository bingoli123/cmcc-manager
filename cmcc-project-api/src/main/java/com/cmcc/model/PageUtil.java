package com.cmcc.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>Create Time: 2018年11月15日 09:24          </p>
 * <p>C@author lidongbin               </p>
 **/
@Getter
@Setter
@Accessors(chain = true)
public class PageUtil implements Serializable {

    private static final long serialVersionUID = -7076014251430294489L;
    /** 开始页码 */
    private Integer pageNum;
    /** 每页大小 */
    private Integer pageSize;
    /** 累计条数 */
    private Integer pageCount;
}
