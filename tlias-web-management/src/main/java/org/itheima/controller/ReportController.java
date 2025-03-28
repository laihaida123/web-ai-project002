package org.itheima.controller;

import lombok.extern.slf4j.Slf4j;
import org.itheima.pojo.JobOption;
import org.itheima.pojo.Result;
import org.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;
    /**
     * 统计员工职位人数
     * @return
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("统计员工职位人数");
        JobOption jobOption = reportService.getEmpJobData();
        log.info("返回的JobOption数据: {}", jobOption);

        return Result.success(jobOption);
    }
    /**
     * 统计员工性别人数
     */
    @GetMapping("/empGenderData")
    public  Result getEmpGenderData(){
        log.info("统计员工性别人数");
        List<Map<String,Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }
}
