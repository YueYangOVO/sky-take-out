package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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

    //根据id查询菜品 用于数据回显 还要查询菜品关联的口味
    DishVO getById(Long id);

    //根据id修改菜品
    Integer update(DishDTO dishDTO);

    //菜品的起售停售
    Integer updateStatus(Integer status, Long id);

    //根据分类id查询数据
    List<DishVO> getByCategoryId(Integer categoryId);
}
