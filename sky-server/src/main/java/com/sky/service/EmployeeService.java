package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO 1
     * @return 1
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO 新增员工的接受类
     * @return 返回影响行数
     */
    Integer save(EmployeeDTO employeeDTO);

    /**
     *  分页查询员工信息，这里带有name属性，name是用来范围查询的，可写可不写
     * @param employeePageQueryDTO 接收参数类
     * @return 返回一共几页，以及查询到当前页的信息
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     *  修改员工账号的启用禁用状态
     * @param status 路径参数，{status} 路径传递启用禁用状态码
     * @param id 查询参数 通过id=0  传递要更新的员工账号id
     * @return 返回影响行数
     */
   Integer updateAccountStatus(Integer status,Long id);

    /**
     * 根据id查询员工信息
     * @param id 查询id
     * @return 返回查询结果
     */
    Employee queryEmployeeById(Integer id);

    /**
     * 修改员工数据
     * @param employeeDTO 接收修改内容
     * @return 返回修改状态
     */
    Integer updateEmployee(EmployeeDTO employeeDTO);
}
