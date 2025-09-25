package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YueYang
 * Created on 2025/9/25 15:40
 * @version 1.0
 */
@RestController
@Api("套餐管理相关接口")
@RequestMapping("/admin/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    /**
     * 新增套餐 套餐中涉及菜品表
     *
     * @param setmealDTO 接收套餐数据
     */
    @PostMapping
    @ApiOperation("新增套餐")
    public Result<String> insert(@RequestBody SetmealDTO setmealDTO) {
        Integer row = setMealService.insert(setmealDTO);
        if (row > 0) return Result.success("添加套餐成功");
        return Result.error("添加菜品失败");

    }

    /**
     * 套餐分页查询 涉及多表
     *
     * @param setmealPageQueryDTO 接收query参数
     * @return 返回查询数据
     */
    @GetMapping("/page")
    @ApiOperation("套餐列表分页查询")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setMealService.pageQuery(setmealPageQueryDTO);
        if (pageResult != null) return Result.success("查询成功", pageResult);
        return Result.error("查询失败");
    }
}
