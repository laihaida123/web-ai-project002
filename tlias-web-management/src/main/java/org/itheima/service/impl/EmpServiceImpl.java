package org.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.itheima.mapper.EmpExprMapper;
import org.itheima.mapper.EmpMapper;
import org.itheima.pojo.*;
import org.itheima.service.EmpLogService;
import org.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private final EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    public EmpServiceImpl(EmpMapper empMapper) {
        this.empMapper = empMapper;
    }
    @Autowired
    private EmpLogService empLogService;

// ---------原始查询方式
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//        //1.调用Mapper接口方法查询数据
//        Long total = empMapper.count();
//        //2.调用mapper接口查询结果列表
//        Integer start = (page - 1) * pageSize;
//        List<Emp> rows = empMapper.list(start, pageSize);
//        //3.封装结果pageresult
//        return new PageResult<Emp>(total, rows);
//    }
    /**
     * 分页查询方法
     * @parampage 页码
     * @parampageSize 每页展示记录数
     * @return
     * 注意事项：
     * 1.定义的SQL语句结尾不能加分号
     * 2.pagehelper仅仅能对紧跟在后面的第一个语句进行分页处理
     */
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize,
//                                String name, Integer gender, LocalDate begin,
//                                LocalDate end){
////        1.设置分页查询参数（pagehelper)
//        PageHelper.startPage(page, pageSize);
//
////        2.执行查询
//        List<Emp> list = empMapper.list(name, gender, begin, end);
//
////        3.解析查询结果，并封装
//        Page<Emp> p = (Page<Emp>) list;
//        return new PageResult<Emp>(p.getTotal(), p.getResult());
//    }

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam){
//        1.设置分页查询参数（pagehelper)
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

//        2.执行查询
        List<Emp> list = empMapper.list(empQueryParam);

//        3.解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) list;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }
    @Transactional(rollbackFor = {Exception.class})//进行事务管理,对指定异常回滚
    @Override
    public void save(Emp emp) throws Exception {
        try {
            //1.保存员工基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);
           // int i = 1/0;

            //2，保存员工工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            //遍历集合，为empid赋值
            if(!CollectionUtils.isEmpty(exprList)){
                exprList.forEach(empExpr ->{
                    empExpr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工信息" + emp);
            empLogService.insertLog(empLog);
        }
    }
    /**
     * 批量删除员工
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids){
        //1.批量删除员工基本信息
        empMapper.deleteByIds(ids);
        //2.批量删除员工的工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }
    /**
     * 查询员工id
     */
    public Emp getInfo(Integer id){
        return empMapper.getById(id);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        //1.根据id修改员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //2.根据id修改员工经历信息
        //2.1先根据id删除原有工作经历
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

        //2.2再添加这个员工新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr->empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

}
