package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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

    @Autowired
    private SetMealDishMapper setMealDishMapper;

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

    //分页查询
    @Override
    public PageResult pageQueryList(DishPageQueryDTO dishPageQueryDTO) {
        //开启分页插件
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        //查询表中内容
        List<DishVO> list = dishMapper.selectList(dishPageQueryDTO);

        PageInfo<DishVO> pageInfo = new PageInfo<>(list);
        //封装返回结果
        PageResult pageResult = new PageResult();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRecords(pageInfo.getList());

        return pageResult;
    }

    //删除菜品，注意菜品关联口味(辣度，忌口那个表)，关联分类表，一个菜品有一个菜品分类；还有起售的菜品不能删除
    @Transactional
    @Override
    public Integer deleteDish(List<Long> ids) {
        //判断当前是否能够删除 起售中的菜品不能删除
        for (Long id : ids) {
            //根据id查询菜品
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus().equals(StatusConstant.ENABLE)) {
                //起售中的商品不能够删除 抛出异常
                throw new DeletionNotAllowedException("起售中的菜品不能删除");
            }
        }
        //判断当前菜品是否关联套餐，关联了套餐不让他删除
        List<Long> setMealIds = setMealDishMapper.getSetMealIdsByDishId(ids);
        if (setMealIds != null && !setMealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        Integer row1 = 0;
        //判断当前菜品的口味是否删除，没有先删除菜品口味
        row1 += dishFlavorMapper.deleteByDishIds(ids);

        //删除菜品口味之后 删除菜品
        row1 += dishMapper.deleteByIds(ids);

        return row1;
    }
}
