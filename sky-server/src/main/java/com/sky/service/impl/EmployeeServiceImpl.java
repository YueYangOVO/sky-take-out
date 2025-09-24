package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传递过来的密码进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO 新增员工的接受类
     * @return 返回影响行数
     * BeanUtils方法是spring带的类 直接用
     */
    @Override
    public Integer save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        //通过对象属性拷贝 一次性的将dto类中的属性拷贝到实体类
        BeanUtils.copyProperties(employeeDTO, employee);
        //设置账号状态
        employee.setStatus(StatusConstant.ENABLE);//0禁止  1正常
        //设置密码， 密码默认是123456 但是还要进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //创建时间 更新时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        // 注意这两个id的用法，存储的是后端网站登录用户的id，我们用的是它的token
        //登录的账户是管理员，这里记录的是当前管理员的id，不是创建者的id
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        //调用持久层
        return employeeMapper.insert(employee);
    }

    /**
     * 分页查询员工信息，这里带有name属性，name是用来范围查询的，可写可不写
     *
     * @param employeePageQueryDTO 接收参数类
     * @return 返回一共几页，以及查询到当前页的信息
     * <p>
     * 注意接收类中有三个参数，第一个name 可选； page当前页 必写；
     * pageSize 每页查询多少条 必写。
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //使用pageHelper分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        //查询表中所有数据
        List<Employee> list = employeeMapper.queryAll(employeePageQueryDTO.getName());

        //封装到pageInfo中
        PageInfo<Employee> pageInfo = new PageInfo<>(list);

        //封装pageResult对象
        PageResult pageResult = new PageResult();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRecords(pageInfo.getList());

        return pageResult;
    }

    /**
     * 修改员工账号的启用禁用状态
     *
     * @param status 路径参数，{status} 路径传递启用禁用状态码
     * @param id     查询参数 通过id=0  传递要更新的员工账号id
     * @return 返回影响行数
     */
    @Override
    public Integer updateAccountStatus(Integer status, Long id) {
   /*     //这里更新方法 要动态更新
        Employee employee = new Employee();
        //根据员工id 修改数据
        employee.setStatus(status);
        employee.setId(id);*/

        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        return employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工信息
     *
     * @param id 查询id
     * @return 返回查询结果
     */
    @Override
    public Employee queryEmployeeById(Integer id) {
        Employee employee = employeeMapper.queryById(id);
        employee.setPassword("****");
        return employee;
    }

    /**
     * 修改员工数据
     *
     * @param employeeDTO 接收修改内容
     * @return 返回修改状态
     */
    @Override
    public Integer updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(BaseContext.getCurrentId());
        return employeeMapper.update(employee);
    }


}
