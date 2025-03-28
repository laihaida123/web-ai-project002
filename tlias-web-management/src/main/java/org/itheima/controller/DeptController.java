package org.itheima.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Delete;
import org.itheima.pojo.Dept;
import org.itheima.pojo.Result;
import org.itheima.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {
    private static final Logger log = LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;
    @RequestMapping("/depts")
    public Result list(){
//        System.out.println("查询所有部门信息");
        log.info("查询所有部门信息");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 删除部门信息
     * @return
     */
//    方式1 httpservletRequest
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request){
//        String idStr = request.getParameter("id");
//        int id = Integer.parseInt(idStr);
//        System.out.println("删除id部门为：" + id);
//        return Result.success();
//    }
//    房市2 @RequestParam 一旦用此方法，必须传递参数（require默认为true）
//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam("id") Integer deptId){
//        System.out.println("删除id部门为：" + deptId);
//        return Result.success();
//    }
    //房市3 前端请求的参数名与方法形参名一致
    @DeleteMapping("/depts")
    public Result delete(Integer id){
//        System.out.println("删除id部门为：" + id);
        log.info("删除id部门为：" + id);
        deptService.deleteById(id);
        return Result.success();
    }
    /**
     * 新增部门
     */
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept){
//        System.out.println("新增部门信息：" + dept);
        log.info("新增部门信息：" + dept);
        deptService.add(dept);
        return Result.success();
    }
    /**
     * 根据id查询部门
     */
    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable("id") Integer deptId){
//        System.out.println("根据id查询部门：" + deptId);
        log.info("根据id查询部门：" + deptId);
        Dept dept = deptService.getById(deptId);
        return Result.success(dept);
    }
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
//        System.out.println("修改部门：" + dept);
        log.info("修改部门：" + dept);
        deptService.update(dept);
        return Result.success();
    }
}
