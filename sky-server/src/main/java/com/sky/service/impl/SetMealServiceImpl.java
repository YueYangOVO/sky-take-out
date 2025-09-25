package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        setmealDTO.getSetmealDishes().forEach(item->item.setSetmealId(setmeal.getId()));
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
}
