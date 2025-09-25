package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YueYang
 * Created on 2025/9/25 10:14
 * @version 1.0
 */
@Mapper
public interface SetMealDishMapper {

    //根据菜品id查询套餐id 这里菜品跟套餐多对多关系，一个菜品id 对应着多个套餐id
    //这里会传递多个菜品id
    List<Long> getSetMealIdsByDishId(List<Long> dishIds);


}
