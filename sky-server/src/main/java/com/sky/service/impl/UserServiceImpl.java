package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YueYang
 * Created on 2025/9/26 19:30
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    //通过授权码 获取openId， 授权码只能用一次; 微信登录服务地址
    public static final String wxLoginUrl = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @param userLoginDTO 里面还有登录的授权码
     * @return 返回封装好的user实体类
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        //调用微信接口服务，获取当前登录用户的openId
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", userLoginDTO.getCode());
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(wxLoginUrl, map);

        //从json中拿到openId
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");

        //判断openId是否为空，为空表示登录失败，抛出业务异常
        if (openid == null) throw new LoginFailedException(MessageConstant.LOGIN_FAILED);

        //判断当前用户是否是新用户
        User user = userMapper.getByOpenId(openid);

        //如果是新用户，自动完成注册
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        //返回该用户实体类
        return user;
    }
}
