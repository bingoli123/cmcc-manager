package com.cmcc.model.project;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2019-2019,
 * Author:   陈腾帅
 * Date:     2019-05-16 09:57:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅         2019-05-16 09:57:57          V1.0              描述
 */
@Setter
@Getter
@Accessors(chain = true)
public class UserProject implements Serializable {
    private static final long serialVersionUID = 592095821992152278L;
    
    private Long id;
    //用户id
    private Long userId;
    //项目id
    private Long projectId;
    //删除标识 N未删除 Y删除
    private String deleteFlag;
    
    private Date createDate;
    
    private Long creator;

}