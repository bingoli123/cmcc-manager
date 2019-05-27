package com.cmcc.dao.cd;

import com.cmcc.model.cd.Codelist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CodelistDao {

    /**
     * 通过主键删除
     *
     * @param rowId
     * @return
     */
    int deleteByPrimaryKey(Long rowId);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(Codelist record);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insertSelective(Codelist record);

    /**
     * 通过主键查询
     *
     * @param rowId
     * @return
     */
    Codelist selectByPrimaryKey(Long rowId);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Codelist record);

    /**
     * 通过主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Codelist record);


    /**
     * 查询
     *
     * @param record
     * @return
     */
    List<Codelist> selectCodeList(Codelist record);
}