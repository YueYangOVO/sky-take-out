package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username 1
     * @return 1
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 向employee表中插入数据
     *
     * @param employee 插入数据的封装类
     * @return 返回影响行数
     */
    @AutoFill(value = OperationType.INSERT)
    Integer insert(Employee employee);


    /**
     * 分页查询员工的所有信息
     *
     * @param name 搜索查询
     * @return 返回查询到的所有结果
     */
    List<Employee> queryAll(String name);

    /**
     * 动态更新员工的数据
     *
     * @param employee 要更新员工的封装类
     * @return 返回影响行数
     */
    @AutoFill(value = OperationType.UPDATE)
    Integer update(Employee employee);

    /**
     * 根据id查询员工信息
     *
     * @param id 查询id
     * @return 返回查询结果
     */
    Employee queryById(Integer id);
}
