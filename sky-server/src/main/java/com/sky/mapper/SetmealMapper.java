package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 新增套餐 套餐中涉及菜品表
     *
     * @param setmeal 接收套餐数据
     */
    @AutoFill(OperationType.INSERT)
    Integer insert(Setmeal setmeal);

    /**
     * 分页查询 套餐列表
     *
     * @param setmealPageQueryDTO 查询参数
     * @return 返回查询对象
     */
    List<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 修改套餐状态
     *
     * @param setmeal 修改对象
     * @return 返回修改行数
     */
    @AutoFill(OperationType.UPDATE)
    Integer update(Setmeal setmeal);

    /**
     *
     * @param id 套餐id
     * @return 返回查询对象
     */
    SetmealVO selectById(Long id);

    /**
     * 根据套餐id查询有多少菜品，注意返回类
     * @param id 套餐id
     * @return 返回集合
     */
    List<DishItemVO> selectById2(Long id);

    /**
     * 批量删除
     * @param ids 批量删除id列表
     * @return 返回删除行数
     */
    Integer delete(List<Long> ids);

    /**
     * 批量查询菜品
     * @param ids 查询id列表
     * @return 返回查询对象集合
     */
    List<SetmealVO> selectByIds(List<Long> ids);

    /**
     * 根据分类id查询套餐列表
     * @param categoryId 分类id
     * @return 返回查询集合
     */
    List<Setmeal> selectByCategoryId(Long categoryId);



}
