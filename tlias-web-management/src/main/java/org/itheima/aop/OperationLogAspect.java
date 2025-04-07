package org.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.itheima.mapper.OperateLogMapper;
import org.itheima.pojo.OperateLog;
import org.itheima.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
@Slf4j
@Aspect
@Component
public class OperationLogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Around("@annotation(org.itheima.anno.Log)")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        //获取执行方法
        Object result = joinPoint.proceed();

        //计算耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        //构建日志实体
        OperateLog olog = new OperateLog();
        olog.setOperateEmpId(getCurrentUserId());
        olog.setOperateTime(LocalDateTime.now());
        olog.setClassName(joinPoint.getTarget().getClass().getName());
        olog.setMethodName(joinPoint.getSignature().getName());
        olog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        olog.setReturnValue(result != null ? result.toString() : "void");
        olog.setCostTime(costTime);

        //输出日志
        log.info("操作日志：{}", olog);
        //保存日志
        operateLogMapper.insert(olog);
        //7.删除threadlocal的数据
        CurrentHolder.remove();
        return result;
    }
    private Integer getCurrentUserId() {
    //根据实际情况获取当前登录用户id
    //这个方法需要根据你的安全框架或上下文来实现
    return CurrentHolder.getCurrentId();//实例返回值
}
}


