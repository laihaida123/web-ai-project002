package org.itheima.service;

import org.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {
    //查询所有的五门数据
    List<Dept> findAll();

    /**
     * 根据id删除部们
     */
    void deleteById(Integer id);

    /**
     * 新增部门
     * @paramdept
     */
    void add(Dept dept);

    Dept getById(Integer deptId);

    void update(Dept dept);
}
