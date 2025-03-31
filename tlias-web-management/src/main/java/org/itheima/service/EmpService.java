package org.itheima.service;

import org.apache.ibatis.annotations.Param;
import org.itheima.pojo.Emp;
import org.itheima.pojo.EmpQueryParam;
import org.itheima.pojo.LoginInfo;
import org.itheima.pojo.PageResult;

import java.util.List;


public interface EmpService {

    /**
     * 新增员工信息
     */
    void save(Emp emp) throws Exception;
    /**
     * 分页查询方法
     * @parampage 页码
     * @parampageSize 每页展示记录数
     * @return
     */
//    PageResult<Emp> page(@RequestParam(defaultValue = "1") Integer page,
//                         @RequestParam(defaultValue = "10") Integer pageSize,
//                         String name, Integer gender, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
//                         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end);
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    public void delete(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(@Param("emp") Emp emp);

    /**
     * 员工登录
     * @param emp
     * @return
     */
    LoginInfo login(Emp emp);
}

