package com.cmcc.model.device;

import java.util.Date;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
/**
 * Copyright (C), 2019-2019,
 * Author:   陈腾帅
 * Date:     2019-05-16 14:00:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          2019-05-16 14:00:51         V1.0              描述
 */
@Setter
@Getter
@Accessors(chain = true)
public class DeviceMaintainLog implements Serializable {
    private static final long serialVersionUID = 753307082135353567L;
    //主键
    private Long id;
    //设备ID
    private Long deviceId;
    //维护时间
    private Date maintainTime;
    //维护人
    private String maintainUser;
    //备注
    private String remark;
    //创建时间
    private Date createTime;
    //创建人
    private Long creator;
    //修改时间
    private Date modifyTime;
    //修改人
    private Long modifier;
    //删除标识  N未删除 Y已删除
    private String deleteFlag;

}