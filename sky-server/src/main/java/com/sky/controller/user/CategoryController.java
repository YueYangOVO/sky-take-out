package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YueYang
 * Created on 2025/9/26 21:02
 * @version 1.0
 */
@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api("用户端分类接口")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 这里我们要根据type查询菜品分类
     * type =1 菜品分类
     * type=2 套餐分类
     * 没有传递查询所有
     *
     * @param type 分类
     * @return 返回查询列表
     */
    @ApiOperation("根据类型查询分类")
    @GetMapping("/list")
    public Result<List<Category>> queryById(@RequestParam(required = false) Integer type) {
        List<Category> list = categoryService.list(type);
        if (list == null) return Result.error("查询失败");
        return Result.success("查询成功", list);

    }


}
