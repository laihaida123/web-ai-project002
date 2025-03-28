package org.itheima.controller;


import lombok.extern.slf4j.Slf4j;
import org.itheima.pojo.*;
import org.itheima.service.ClazzSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {
    @Autowired
    private ClazzSerivce clazzService;
    /**
     * 分页查询
     */
    @GetMapping
    public Result page(String name,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询，参数：{} {}", page, pageSize);
        PageResult<Clazz> pageResult = clazzService.page(name, begin, end, page, pageSize);
        return Result.success(pageResult);
    }
    /**
     * 根据ID删除班级
     * @param ids -- 数组
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@RequestParam(value="id",required=false) Integer ids){
        log.info("删除员工，id为：{}", ids);
        clazzService.deleteById(ids);
        return Result.success();
    }
}
