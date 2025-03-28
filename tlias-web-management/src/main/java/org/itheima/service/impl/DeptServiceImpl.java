package org.itheima.service.impl;

import org.itheima.mapper.DeptMapper;
import org.itheima.pojo.Dept;
import org.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Override
    public List<Dept> findAll() {
    return deptMapper.findAll();
    }
    /**
     * 根据id删除部门
     */
    @Override
    public void deleteById(Integer id){
        deptMapper.deleteById(id);
    }
    @Override
    public void add(@RequestBody Dept dept){
//        1.补全基础属性- createTime,updateTime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
//        2.调用Mapper接口方法数据
        deptMapper.insert(dept);
    }
    @Override
    public Dept getById(Integer deptId){
        return deptMapper.getById(deptId);
    }

    @Override
    public void update(Dept dept) {
        //1. 补全基础属性-updateTime
        dept.setUpdateTime(LocalDateTime.now());

        //2.调用Mapper接口方法更新部门
        deptMapper.update(dept);
    }
}

