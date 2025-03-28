package org.itheima.controller;

import lombok.extern.slf4j.Slf4j;
import org.itheima.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    @PostMapping("/upload")
    public Result upload(String name, Integer age, MultipartFile file) throws IOException {
        log.info("接收参数：{}，{}，{}",name, age, file);
        //获取原始文件名
        String originalFilename =  file.getOriginalFilename();//1.jpg 2.png
        //新的构建的文件名
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));//以最后一个为.开始截取
        String newFileName = UUID.randomUUID().toString() + extension;

        //保存文件
        file.transferTo(new File("D:\\工作区\\计组" + originalFilename));
        return Result.success();
    }
}
