package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YueYang
 * Created on 2025/9/24 16:06
 * @version 1.0
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    //新增菜品
    @Transactional
    @Override
    public Integer save(DishDTO dishDTO) {
        //这里要分两段添加数据 dish表添加菜品， dish_flavor添加菜品的口味

        //插入菜品表
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        Integer row1 = dishMapper.insert(dish);

        //获取dishId，这里插入数据后，会将id返回到dish对象中
        Long dishId = dish.getId();

        //像口味表中添加多条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        Integer row2 = 0;
        if (flavors != null && !flavors.isEmpty()) {
            //遍历flavors口味集合 方便插入dishId
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            row2 = dishFlavorMapper.insertBatch(flavors);
        }

        return row1 + row2;
    }
}
