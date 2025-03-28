package org.itheima.controller;

import lombok.extern.slf4j.Slf4j;
import org.itheima.pojo.Emp;
import org.itheima.pojo.EmpQueryParam;
import org.itheima.pojo.PageResult;
import org.itheima.pojo.Result;
import org.itheima.service.EmpService;
import org.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 员工管理controller
 */
@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;
    @Autowired
    private ReportService reportService;

    /**
     * 分页查询
     */
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询，参数：{}",empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 新增员工
     * @param emp
     * @return
     * @throws Exception
     */
    @PostMapping
    public Result save(@RequestBody Emp emp) throws Exception {
        log.info("新增员工，员工信息：{}",emp);
        empService.save(emp);
        return Result.success();
    }
    /**
     * 删除员工
     * @param ids -- 数组
     * @return
//     */
//    @DeleteMapping
//    public Result delete(Integer[] ids){
//        log.info("删除员工，id为：{}", Arrays.toString(ids));
//        return Result.success();
//    }
    /**
     * 删除员工
     * @param ids -- 数组
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam(value="ids",required=false) List<Integer> ids){
        log.info("删除员工，id为：{}", ids);
        empService.delete(ids);
        return Result.success();
    }
    /**
     * 根据id查询员工信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询员工信息:{}",id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }
    /**
     * 修改员工id
     */
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工:{}",emp);
        empService.update(emp);
        return Result.success();
    }

}
