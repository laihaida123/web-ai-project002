package org.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.itheima.pojo.Emp;
import org.itheima.pojo.EmpQueryParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// -----------------------------------原始分页查询--------------------------------------
/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {
    /**
     * 查询总记录数
     * @return
     */
//    @Select("select count(*) from emp e left outer join dept d on e.dept_id = d.id")
//    public Long count();

//    @Select("select e.*, d.name deptName from emp e left outer join dept d on e.dept_id = d.id " +
//            "order by e.update_time desc limit #{start}, #{pageSize}")
//    public List<Emp> list(@Param("start") Integer start, @Param("pageSize") Integer pageSize);
//    @Select("select e.*, d.name deptName from emp e left outer join dept d on e.dept_id = d.id " +
//            "order by e.update_time desc")
//    public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);

    /**
     * 条件查询员工信息
     * @param empQueryParam
     * @return
     */
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息
     */
    @Options(useGeneratedKeys = true , keyProperty = "id")//获取到生成的主键，--主键返回
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) VALUES(#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId},#{createTime}, #{updateTime})")
    void insert(Emp emp);
    /**
     * 删除ID批量删除员工基本信息
     */
    void deleteByIds(List<Integer> ids);
    /**
     * 根据id查询员工信息及其经历信息
     */
    Emp getById(Integer id);
    /**
     * 更新员工基本信息
     */
    void updateById(Emp emp);
    /**
     * 统计员工职位人数
     */
    @MapKey("pos")
    List<Map<String, Object>> countEmpJobData();

    /**
     * 统计员工性别人数
     * @return
     */
    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();
}
