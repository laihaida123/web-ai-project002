package org.itheima.controller;

import lombok.extern.slf4j.Slf4j;
import org.itheima.pojo.PageResult;
import org.itheima.pojo.Result;
import org.itheima.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 条件分页查询
     */
    @GetMapping
    public Result page(String name ,
                       Integer degree,
                       Integer clazzId,
                       @RequestParam(defaultValue = "1") Integer page ,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        PageResult pageResult = studentService.page(name,degree,clazzId,page,pageSize);
        return Result.success(pageResult);
    }
}
