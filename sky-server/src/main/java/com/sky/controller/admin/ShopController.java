package com.sky.controller.admin;

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
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String SHOP_STATUS = "SHOP_STATUS";

    /**
     * 设置店铺营业状态
     * 这里将店铺状态存储到redis当中
     *
     * @param status 状态码 0打烊   1营业
     * @return 返回修改结果
     */
    @ApiOperation("修改商店营业状态")
    @PutMapping("/{status}")
    public Result<String> setStatus(@PathVariable Integer status) {
        log.info("设置营业状态:{}", status == 0 ? "打烊" : "营业中");
        redisTemplate.opsForValue().set(SHOP_STATUS, status);
        return Result.success("修改成功");
    }

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
