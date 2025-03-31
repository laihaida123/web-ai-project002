package org.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装登录结果
 */
@Data//添加getter/setter方法
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private Integer id;
    private String username;
    private String name;
    private String token;
}
