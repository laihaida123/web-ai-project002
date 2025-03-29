package org.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.itheima.exception.BusinessException;
import org.itheima.mapper.ClazzMapper;
import org.itheima.mapper.StudentMapper;
import org.itheima.pojo.Clazz;
import org.itheima.pojo.PageResult;
import org.itheima.pojo.Result;
import org.itheima.service.ClazzSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzSerivce {
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private StudentMapper studentMapper;
    /**
     * 分页查询方法
     * @parampage 页码
     * @parampageSize 每页展示记录数
     * @return
     * 注意事项：
     * 1.定义的SQL语句结尾不能加分号
     * 2.pagehelper仅仅能对紧跟在后面的第一个语句进行分页处理
     */
    @Override
    public PageResult page(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize){
////        1.设置分页查询参数（pagehelper)
        PageHelper.startPage(page, pageSize);
////        2.执行查询
        List<Clazz> dataList = clazzMapper.list(name, begin, end);
        Page<Clazz> p = (Page<Clazz>) dataList;

        return new PageResult(p.getTotal(), p.getResult());
    }

//    删除班级信息
    @Override
    public void deleteById(Integer ids){
        //1. 查询班级下是否有学员
        Integer count = studentMapper.countByClazzId(ids);
        if(count > 0){
            throw new BusinessException("班级下有学员, 不能直接删除~");
        }
        //2. 如果没有, 再删除班级信息
        clazzMapper.deleteById(ids);
    }
//    添加班级信息
    @Override
    public void save(Clazz clazz){
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }
//    根据id查询班级信息
    @Override
    public Clazz getInfo(Integer id){
        return clazzMapper.getInfo(id);
    }
//    更新信息
    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
    }
//    查询所有班级信息
    @Override
    public List<Clazz> findAll() {
        return clazzMapper.findAll();
    }
}
