package org.itheima.service;

import org.itheima.pojo.JobOption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ReportService {

    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();
}
