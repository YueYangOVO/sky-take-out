package com.sky.controller.user;

/**
 * @author YueYang
 * Created on 2025/9/26 15:17
 * @version 1.0
 */

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author YueYang
 * Created on 2025/9/26 15:01
 * @version 1.0
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String SHOP_STATUS = "SHOP_STATUS";

    /**
     * 获取店铺营业状态
     *
     * @return 营业状态码
     */
    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        log.info("获取店铺营业状态: {}", Objects.equals(status, 1) ? "营业中" : "打烊");
        return Result.success("获取成功", status);
    }
}