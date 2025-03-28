package org.itheima.mapper;

import org.apache.ibatis.annotations.*;
import org.itheima.pojo.Dept;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface DeptMapper {

//    private AdviceModeImportSelector adviceModeImportSelector;
//
//    DeptMapper(AdviceModeImportSelector adviceModeImportSelector) {
//        this.adviceModeImportSelector = adviceModeImportSelector;
//    }

    /**
     * 查询所有部门信息
     * @return
     */
    //f方式1：手动结果映射
//    @Results({
//            @Result(column = "create_time", property = "createTime"),
//            @Result(column = "update_time", property = "updateTime")
//    })
//    方式2：起别名
//    @Select("select id, name, create_time createTime, update_time updateTime from dept order by update_time desc")
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();

    /**
     * 根据id删除部门
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);


    /**
     * 新增部门
     */
    @Insert("insert into dept(name, create_time, update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);
    /**
     * 根据Id查询部门数据
     */
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    /**
     * 更新部门数据
     */
    @Update("update dept set name = #{name} , update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
