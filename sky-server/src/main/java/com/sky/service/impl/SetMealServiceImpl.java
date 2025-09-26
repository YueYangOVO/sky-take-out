package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author YueYang
 * Created on 2025/9/25 15:47
 * @version 1.0
 */
@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    /**
     * 新增套餐 套餐中涉及菜品表
     *
     * @param setmealDTO 接收套餐数据
     */
    @Override
    @Transactional
    public Integer insert(SetmealDTO setmealDTO) {
        Integer row = 0;

        //先插入套餐表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        row += setmealMapper.insert(setmeal);
        //插入套餐 菜品表的关联信息 setmeal_dish这里添加关联信息
        setmealDTO.getSetmealDishes().forEach(item -> item.setSetmealId(setmeal.getId()));
        row += setMealDishMapper.insert(setmealDTO.getSetmealDishes());
        return row;
    }

    /**
     * 分页查询 套餐列表
     *
     * @param setmealPageQueryDTO 查询参数
     * @return 返回查询对象
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //开启分页插件
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        //根据条件查询列表
        List<SetmealVO> list = setmealMapper.pageQuery(setmealPageQueryDTO);

        //封装pageInfo对象
        PageInfo<SetmealVO> pageInfo = new PageInfo<>(list);

        //封装返回对象
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 套餐起售停售
     *
     * @param status 套餐状态
     * @param id     修改状态套餐的id
     * @return 返回修改状态
     */
    @Override
    public Integer updateStatus(Integer status, Long id) {
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        return setmealMapper.update(setmeal);
    }

    /**
     * @param id 套餐id
     * @return 返回查询对象
     */
    @Override
    public SetmealVO queryById(Long id) {
        return setmealMapper.selectById(id);
    }

    /**
     * 更新套餐
     *
     * @param setmealDTO 更新对象
     * @return 返回影响行数
     */
    @Transactional
    @Override
    public Integer update(SetmealDTO setmealDTO) {
        Integer row = 0;
        //先更新set_dish表， 删除之前的数据，添加更新的数据 setMealId需要添加
        List<Long> ids = new ArrayList<>();
        ids.add(setmealDTO.getId());
        row += setMealDishMapper.deleteBySetMealIds(ids);
        //添加套餐中的菜品
        setmealDTO.getSetmealDishes().forEach(item -> item.setSetmealId(setmealDTO.getId()));
        row += setMealDishMapper.insert(setmealDTO.getSetmealDishes());

        //修改套餐表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        row += setmealMapper.update(setmeal);

        return row;
    }

    /**
     * 批量删除
     *
     * @param ids 批量删除id列表
     * @return 返回删除行数
     */
    @Transactional
    @Override
    public Integer deleteBatch(List<Long> ids) {
        //起售的套餐不能够删除
        List<SetmealVO> list = setmealMapper.selectByIds(ids);
        list.forEach(item -> {
            if (item.getStatus().equals(StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });


        Integer row = 0;
        //先删除套餐关联的菜品
        row += setMealDishMapper.deleteBySetMealIds(ids);
        //再删除套餐
        row += setmealMapper.delete(ids);

        return row;
    }


}
