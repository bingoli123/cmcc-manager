package com.cmcc.dao.user;

import com.cmcc.model.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User queryByMobile(String mobile);

    User queryByUserName(String userId);

    /**
     * 查询公司id下的所有用户
     * @param companyId
     * @return
     * @throws Exception
     */
    List<User> queryUserByCompany (@Param("companyId") Long companyId,
                                   @Param("userName") String userName) throws  Exception;
}