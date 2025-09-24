package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工登录相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO 1
     * @return 1`
     */
    @ApiOperation(value = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出登录
     *
     * @return 返回退出登录状态
     */
    @ApiOperation(value = "员工退出登录")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工信息
     *
     * @param employeeDTO 接收前端传递过来的json数据
     * @return 请求路径/admin/employee 不需要管，设置post请求即可
     */
    @PostMapping()
    @ApiOperation("新增员工")
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工: {}", employeeDTO);
        Integer row = employeeService.save(employeeDTO);
        if (row > 0) return Result.success("添加员工成功");
        return Result.error("添加员工失败");
    }

    /**
     * 分页查询员工信息，这里带有name属性，name是用来范围查询的，可写可不写
     *
     * @param employeePageQueryDTO 接收参数类
     * @return 返回一共几页，以及查询到当前页的信息
     */
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询员工信息，查询参数: {}", employeePageQueryDTO);
        PageResult queryList = employeeService.pageQuery(employeePageQueryDTO);
        if (queryList == null) return Result.error("分页查询失败");
        return Result.success("分页查询成功", queryList);
    }


    /**
     * 修改员工账号的启用禁用状态
     *
     * @param status 路径参数，{status} 路径传递启用禁用状态码
     * @param id     查询参数 通过id=0  传递要更新的员工账号id
     * @return 返回响应数据
     */
    @PostMapping("/status/{status}")
    @ApiOperation("员工账号启用禁用")
    public Result<String> updateAccountStatus(@PathVariable Integer status, Long id) {
        log.info("启用禁用员工账号: id={}, status={}", id, status);
        Integer row = employeeService.updateAccountStatus(status, id);
        String rightMsg = status == 1 ? "启用成功" : "禁用成功";
        String errMsg = status == 1 ? "启用失败" : "禁用失败";
        if (row > 0) return Result.success(rightMsg);
        return Result.error(errMsg);
    }

    /**
     * 根据id查询员工信息
     *
     * @param id 查询id
     * @return 返回查询结果
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result<Employee> queryEmployeeById(@PathVariable Integer id) {
        log.info("根据id查询员工数据: id={}", id);
        Employee employee = employeeService.queryEmployeeById(id);
        if (employee == null) return Result.error("查询员工信息失败");
        return Result.success("查询员工信息成功", employee);
    }


    /**
     * 修改员工数据
     *
     * @param employeeDTO 接收修改内容
     * @return 返回修改状态
     */
    @PutMapping
    @ApiOperation("修改员工数据")
    public Result<String> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("修改员工数据: {}", employeeDTO);
        Integer row = employeeService.updateEmployee(employeeDTO);
        if (row > 0) return Result.success("修改员工成功");
        return Result.error("修改员工失败");
    }


}
