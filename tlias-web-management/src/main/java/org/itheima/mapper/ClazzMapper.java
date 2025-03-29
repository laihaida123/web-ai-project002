package org.itheima.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.itheima.pojo.Clazz;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ClazzMapper {

    /**
     * 动态条件查询
     */
    List<Clazz> list(String name, LocalDate begin, LocalDate end);
    /**
     * 根据ID删除班级
     * @param ids
     */
    void deleteById(Integer ids);
    /**
     * 添加班级信息
     */
    void insert(Clazz clazz);
    /**
     * 根据ID查询班级详情
     */
    Clazz getInfo(Integer id);
    /**
     * 动态更新班级信息
     */
    void update(Clazz clazz);
    /**
     * 查询全部班级
     */
    List<Clazz> findAll();
}
