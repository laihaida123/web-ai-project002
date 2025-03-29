package org.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.itheima.pojo.Student;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    /**
     * 添加学生
     */
    void insert(Student student);
    /**
     * 动态条件查询
     */
    List<Student> list(String name, Integer degree, Integer clazzId);
    /**
     * 根据ID查询学生信息
     */
    Student getById(Integer id);
    /**
     * 修改学生信息
     */
    void update(Student student);
    /**
     * 批量删除学生信息
     */
    void delete(List<Integer> ids);
    /**
     * 违纪处理
     */
    void updateViolation(Integer id, Integer score);
    /**
     * 统计班级人数
     */
    @MapKey("cname")
    List<Map<String,Object>> getStudentCount();
    /**
     * 统计学员学历
     */
    @MapKey("name")
    List<Map> countStudentDegreeData();
    /**
     * 查询某个班级下的学生人数
     * @param id
     * @return
     */
    Integer countByClazzId(Integer id);
    /**
     * 批量插入
     */
    void insertBatch(List<Student> studentList);
}
