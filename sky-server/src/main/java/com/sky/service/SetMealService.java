package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

/**
 * @author YueYang
 * Created on 2025/9/25 15:46
 * @version 1.0
 */
public interface SetMealService {

    /**
     * 新增套餐 套餐中涉及菜品表
     *
     * @param setmealDTO 接收套餐数据
     */
    Integer insert(SetmealDTO setmealDTO);

    /**
     * 分页查询 套餐列表
     *
     * @param setmealPageQueryDTO 查询参数
     * @return 返回查询对象
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}
