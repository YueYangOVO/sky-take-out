package com.sky.controller.user;

import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YueYang
 * Created on 2025/9/26 21:13
 * @version 1.0
 */
@Api("菜品管理相关接口")
@RestController("userDishController")
@Slf4j
@RequestMapping("/user/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    //根据分类id查询菜品
    @GetMapping("/list")
    public Result<List<DishVO>> getByCategoryId(Integer categoryId) {
        List<DishVO> list = dishService.getByCategoryId(categoryId);
        if (list == null) return Result.error("查询失败");
        return Result.success("查询成功", list);
    }


}
