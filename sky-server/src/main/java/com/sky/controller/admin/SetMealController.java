package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
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

    /**
     * 套餐起售停售
     *
     * @param status 套餐状态
     * @param id     修改状态套餐的id
     * @return 返回操作结果
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改套餐状态")
    public Result<String> updateStatus(@PathVariable Integer status, Long id) {
        Integer row = setMealService.updateStatus(status, id);
        if (row > 0) return Result.success();
        return Result.error("操作失败");
    }

    /**
     * 根据id查询套餐 这里设计三张表
     * 查询套餐表 分类表 套餐菜品表
     * @param id 套餐id
     * @return 返回查询结果
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> queryById(@PathVariable Long id){
        SetmealVO setmealVO = setMealService.queryById(id);
        if (setmealVO != null) return Result.success("查询成功", setmealVO);
        return Result.error("查询失败");
    }
}
