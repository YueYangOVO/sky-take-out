package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

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

    /**
     * 套餐起售停售
     * @param status 套餐状态
     * @param id 修改状态套餐的id
     * @return  返回修改状态
     */
    Integer updateStatus(Integer status, Long id);


    /**
     *
     * @param id 套餐id
     * @return 返回查询对象
     */
    SetmealVO queryById(Long id);
}
