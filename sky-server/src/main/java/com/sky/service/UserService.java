package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @author YueYang
 * Created on 2025/9/26 19:31
 * @version 1.0
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param userLoginDTO 里面还有登录的授权码
     * @return 返回封装好的user实体类
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
