package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YueYang
 * Created on 2025/9/24 15:58
 * @version 1.0
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关管理")
public class DishController {

    @Autowired
    private DishService dishService;

    //添加菜品
    @PostMapping
    @ApiOperation("新增菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}",dishDTO);
        Integer row = dishService.save(dishDTO);
        if(row>0) return Result.success("添加菜品成功");
        return Result.error("添加菜品失败");
    }
}
