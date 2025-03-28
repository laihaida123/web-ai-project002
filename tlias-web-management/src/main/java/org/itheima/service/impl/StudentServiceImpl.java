package org.itheima.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.itheima.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentServiceImpl {
    @Autowired
    private StudentService studentService;
}
