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
    /**
     * 添加班级
     */
    @PostMapping
    public Result save(@RequestBody Clazz clazz){
        clazzService.save(clazz);
        return Result.success();
    }

    /**
     * 根据ID查询班级详情
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        Clazz clazz = clazzService.getInfo(id);
        return Result.success(clazz);
    }
    /**
     * 更新班级信息
     */
    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        clazzService.update(clazz);
        return Result.success();
    }
    /**
     * 查询全部班级
     */
    @GetMapping("/list")
    public Result findAll(){
        List<Clazz> clazzList = clazzService.findAll();
        return Result.success(clazzList);
    }
}
