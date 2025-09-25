package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId `
     * @return `
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    //菜品表中添加数据
    @AutoFill(OperationType.INSERT)
    Integer insert(Dish dish);

    //菜品分页查询
    List<DishVO> selectList(DishPageQueryDTO dishPageQueryDTO);


    //根据id查询菜品
    Dish getById(Long id);

    //根据id批量删除
    Integer deleteByIds(List<Long> ids);

    //根据id查询菜品 用于数据回显 还要查询菜品关联的口味
    DishVO selectById(Long id);

    //根据id修改菜品
    @AutoFill(OperationType.UPDATE)
    Integer update(DishDTO dishDTO);
}
