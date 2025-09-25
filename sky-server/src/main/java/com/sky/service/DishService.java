package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

/**
 * @author YueYang
 * Created on 2025/9/24 16:06
 * @version 1.0
 */
public interface DishService {
    //新增菜品
    Integer save(DishDTO dishDTO);

    //分页查询
    PageResult pageQueryList(DishPageQueryDTO dishPageQueryDTO);

    //删除菜品，注意菜品关联口味(辣度，忌口那个表)，关联分类表，一个菜品有一个菜品分类；还有起售的菜品不能删除
    Integer deleteDish(List<Long> ids);
}
