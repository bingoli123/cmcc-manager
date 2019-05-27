package com.cmcc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2019-2019,
 * FileName: ArrayListUtitl
 * @Author:   陈腾帅
 * Date:     2019 5 17 0017 15:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 陈腾帅          修改时间           V1.0              描述
 */
public class ArrayListUtitl {

    /**
     * 根据原集合和目的集合求出查缉 需要删除的
     *
     * @param sourceLong 源集合
     * @param destLong   目的集合
     * @return
     */
    public static List<Long> deleteListLong(List<Long> sourceLong, List<Long> destLong) {
        //需要删除的集合
        List<Long> deleteList = new ArrayList<>();
        for (Long id : sourceLong) {
            if (!destLong.contains(id)) {
                deleteList.add(id);
            }
        }
        return deleteList;
    }

    /**
     * 根据原集合和目的集合求出查缉 需要新增的
     *
     * @param sourceLong 源集合
     * @param destLong   目的集合
     * @return
     */
    public static List<Long> insertListLong(List<Long> sourceLong, List<Long> destLong) {
        //需要新增的角色集合
        List<Long> insertList = new ArrayList<>();
        for (Long id : destLong) {
            if (!sourceLong.contains(id)) {
                insertList.add(id);
            }
        }
        return insertList;
    }
}
