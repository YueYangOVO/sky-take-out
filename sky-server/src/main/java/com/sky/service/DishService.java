package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * @author YueYang
 * Created on 2025/9/24 16:06
 * @version 1.0
 */
public interface DishService {
    //新增菜品
    Integer save(DishDTO dishDTO);
}
