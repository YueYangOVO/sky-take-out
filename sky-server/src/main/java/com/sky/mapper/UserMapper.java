package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author YueYang
 * Created on 2025/9/26 20:26
 * @version 1.0
 */
@Mapper
public interface UserMapper {

    /**
     * 登录时根据openID获取是新用户还是老用户
     * @param openId openId字段
     * @return 返回查询结果
     */
    User getByOpenId(String openId);

    /**
     * 插入数据
     * @param user
     */
    void insert(User user);
}
