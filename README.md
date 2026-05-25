# 学生管理系统（Student Management System）

基于 Spring Boot + Vue 3 的教务管理平台，实现学生档案、班级管理、教职工管理、操作审计等核心功能，支持多角色协同办公。

## 📖 项目背景

随着学校规模扩大，传统纸质管理效率低下且易出错。本系统旨在提供一套数字化管理方案，实现：

- 学生档案的统一电子化管理（含学籍、奖惩、班级归属）
- 班级教学进度的可视化监控（开课率 / 结课率 / 在籍人数）
- 关键操作的全流程追溯（增删改查行为审计）

## 🧱 技术架构

| 层次       | 技术栈                                                                 |
| ---------- | ---------------------------------------------------------------------- |
| 前端       | Vue 3 + Element Plus + ECharts                                        |
| 后端       | Spring Boot + Spring Security + JWT + MyBatis-Plus                    |
| 数据库     | MySQL 8.0 + Redis 7.0（缓存 / Session 管理）                          |
| 开发工具   | IntelliJ IDEA + VS Code + Git + Maven                                 |
| 日志       | Logback                                                               |

### 后端分层设计（Controller-Service-Mapper）

- **Controller**：接收前端请求，调用 Service 层
- **Service**：核心业务逻辑处理
- **Mapper**：通过 MyBatis-Plus 操作 MySQL 数据库（支持 XML 多表联查与动态 SQL）

## ✅ 核心功能模块

| 模块         | 功能描述                                                       |
| ------------ | -------------------------------------------------------------- |
| 学生管理     | 学生档案 CRUD、批量导入 / 导出、多条件组合查询                 |
| 班级管理     | 班级创建 / 结课、班主任分配、班级状态监控                      |
| 教职工管理   | 教师档案管理、用户账号绑定（1:1）                              |
| 系统日志     | 操作审计（增删改记录）、登录日志                               |
| 权限控制     | 基于 RBAC 的角色管理（管理员 / 班主任 / 教师）、接口级权限控制 |

## 🗄️ 数据库设计（部分核心表）

### 学生表（student）

| 字段名           | 类型         | 约束                        | 说明               |
| ---------------- | ------------ | --------------------------- | ------------------ |
| student_id       | INT          | PRIMARY KEY                 | 学生编号           |
| name             | VARCHAR(50)  | NOT NULL                    | 姓名               |
| id_card          | VARCHAR(20)  | UNIQUE                      | 身份证号           |
| phone            | VARCHAR(20)  |                             | 电话               |
| violation_count  | INT          | DEFAULT 0                   | 违规次数           |
| violation_deduct | DECIMAL(5,2) | DEFAULT 0.00                | 违规扣分           |
| college_id       | INT          | FOREIGN KEY (college)       | 所属学院编号       |

### 班级表（class）

| 字段名            | 类型         | 约束                  | 说明           |
| ----------------- | ------------ | --------------------- | -------------- |
| class_id          | INT          | PRIMARY KEY           | 班级编号       |
| class_name        | VARCHAR(50)  | NOT NULL              | 班级名称       |
| head_teacher_id   | INT          | FOREIGN KEY (teacher) | 班主任编号     |
| college_id        | INT          | FOREIGN KEY (college) | 所属学院编号   |
| create_time       | DATETIME     | DEFAULT CURRENT_TIMESTAMP | 创建时间   |

### 操作日志表（op_log）

| 字段名         | 类型          | 约束                  | 说明               |
| -------------- | ------------- | --------------------- | ------------------ |
| log_id         | INT           | PRIMARY KEY           | 日志编号           |
| class_name     | VARCHAR(100)  | NOT NULL              | 类名               |
| operator_id    | INT           | FOREIGN KEY (employee)| 操作员工编号       |
| op_time        | DATETIME      | DEFAULT CURRENT_TIMESTAMP | 操作时间       |
| method_name    | VARCHAR(100)  | NOT NULL              | 方法名称           |
| method_params  | TEXT          |                       | 方法参数           |
| time_cost      | INT           | NOT NULL              | 耗时（毫秒）       |

> 完整表结构及关系见项目中的 `docs/schema.sql` 或设计说明书。

## 🔐 安全设计

### 1. 认证与授权

- 使用 **JWT（JSON Web Token）** 进行无状态身份认证
- JWT 结构：Header（签名算法） + Payload（用户ID、权限、过期时间） + Signature（防篡改签名）
- 结合 **Spring Security** 实现接口级 RBAC 权限控制

### 2. 数据安全

- 自定义 **拦截器（Interceptor）** 在请求进入 Controller 之前校验 JWT Token
- 未携带或 Token 失效的请求直接拦截，防止伪造 URL 绕过前端登录校验

### 3. 异常处理

- 全局异常处理器 `@RestControllerAdvice` 统一捕获业务异常与系统异常
- 避免堆栈信息泄露，统一返回 `{code, message, data}` 格式

## 📊 数据可视化

- 集成 **ECharts** 实现班级教学进度、违规统计等数据的图表展示
- 支持管理者进行数据驱动的决策分析

## 🚀 快速开始（本地运行）

### 环境要求

- JDK 11+
- Maven 3.6+
- MySQL 8.0+
- Redis 7.0+
- Node.js 16+（前端）

### 后端启动步骤

```bash
# 1. 克隆项目
git clone https://github.com/your-username/student-management-system.git
cd student-management-system/backend

# 2. 创建数据库并导入初始数据
mysql -u root -p < docs/init.sql

# 3. 修改 application.yml 中的数据库和 Redis 配置

# 4. 编译并运行
mvn clean install
mvn spring-boot:run
