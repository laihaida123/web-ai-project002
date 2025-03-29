package org.itheima.service;

import org.itheima.pojo.ClazzCountOption;
import org.itheima.pojo.JobOption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 统计员工职位人数
     */
    JobOption getEmpJobData();
    /**
     * 统计员工性别人数
     */
    List<Map<String, Object>> getEmpGenderData();
    /**
     * 统计学历人数
     */
    List<Map> getStudentDegreeData();
    /**
     * 统计班级人数
     */
    ClazzCountOption getStudentCountData();
}
