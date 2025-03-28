package org.itheima.service;

import org.itheima.pojo.Clazz;
import org.itheima.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;


public interface ClazzSerivce {
    /**
     * 条件分页查询
     * @param name
     * @param begin
     * @param end
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<Clazz> page(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize);

    void deleteById(Integer ids);
}
