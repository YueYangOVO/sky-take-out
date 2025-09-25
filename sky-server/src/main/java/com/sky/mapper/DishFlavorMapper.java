package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YueYang
 * Created on 2025/9/24 16:59
 * @version 1.0
 */
@Mapper
public interface DishFlavorMapper {
    //批量插入
    Integer insertBatch(List<DishFlavor> flavors);

    //根据菜品id批量删除菜品的口味
    Integer deleteByDishIds(List<Long> dishIds);
}
