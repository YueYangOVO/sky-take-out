package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
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
 * Created on 2025/9/26 21:17
 * @version 1.0
 */
@Slf4j
@Api(tags = "用户端套餐接口")
@RestController("userSetMealController")
@RequestMapping("/user/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    @ApiOperation("根据分类id查询套餐")
    @GetMapping("/list")
    public Result<List<Setmeal>> queryByCategoryId(Long categoryId) {
        List<Setmeal> list = setMealService.queryByCategoryId(categoryId);
        if (list == null) return Result.error("查询失败");
        return Result.success("查询成功", list);
    }

    @ApiOperation("根据套餐id查询含有哪些菜品")
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> queryDishes(@PathVariable Long id){
        List<DishItemVO> list = setMealService.queryDishes(id);
        if (list == null) return Result.error("查询失败");
        return Result.success("查询成功", list);
    }


}
