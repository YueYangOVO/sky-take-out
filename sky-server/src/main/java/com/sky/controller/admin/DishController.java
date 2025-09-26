package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YueYang
 * Created on 2025/9/24 15:58
 * @version 1.0
 * 菜品管理
 */
@Slf4j
@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关管理")
public class DishController {

    @Autowired
    private DishService dishService;

    //添加菜品
    @PostMapping
    @ApiOperation("新增菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        Integer row = dishService.save(dishDTO);
        if (row > 0) return Result.success("添加菜品成功");
        return Result.error("添加菜品失败");
    }


    //菜品分页查询 这里是通过 key=value添加的，因此我们不用json接收
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageQueryList(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询");
        PageResult pageResult = dishService.pageQueryList(dishPageQueryDTO);
        if (pageResult == null) return Result.error("分页查询失败");
        return Result.success("分页查询成功", pageResult);
    }

    //删除菜品，注意菜品关联口味(辣度，忌口那个表)，关联分类表，一个菜品有一个菜品分类；还有起售的菜品不能删除
    @DeleteMapping
    @ApiOperation("根据id批量删除菜品")
    public Result<String> deleteDish(@RequestParam List<Long> ids) {
        log.info("批量删除菜品:{} ", ids);
        Integer rows = dishService.deleteDish(ids);
        if (rows > 0) return Result.success("删除成功");
        return Result.error("删除失败");
    }

    //根据id查询菜品 用于数据回显 还要查询菜品关联的口味
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品:{}", id);
        DishVO dishVO = dishService.getById(id);
        if (dishVO != null) return Result.success("查询成功", dishVO);
        return Result.error("查询失败");
    }

    //根据分类id查询菜品
    @GetMapping("/list")
    public Result<List<DishVO>> getByCategoryId(Integer categoryId) {
        List<DishVO> list = dishService.getByCategoryId(categoryId);
        if (list == null) return Result.error("查询失败");
        return Result.success("查询成功", list);
    }

    //根据id修改数据
    @PutMapping
    @ApiOperation("根据id修改菜品")
    public Result<String> update(@RequestBody DishDTO dishDTO) {
        log.info("根据id修改菜品:{}", dishDTO);
        Integer row = dishService.update(dishDTO);
        if (row > 0) return Result.success("修改菜品成功");
        return Result.error("修改数据失败");
    }

    //菜品的起售停售
    @PostMapping("/status/{status}")
    @ApiOperation("菜品的起售停售")
    public Result<String> updateStatus(@PathVariable Integer status, Long id) {
        log.info("菜品的起售停售:{}", status);
        Integer row = dishService.updateStatus(status, id);
        if (row > 0) return Result.success();
        return Result.error("操作失败");
    }


}
