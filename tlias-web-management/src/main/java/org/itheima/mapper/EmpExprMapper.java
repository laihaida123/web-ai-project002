package org.itheima.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.itheima.pojo.EmpExpr;

import java.util.List;

/**
 * 员工工作经历
 */
@Mapper
public interface EmpExprMapper {
    /**
     * 批量保存员工经历信息
     */
    void insertBatch(List<EmpExpr> exprList);
    /**
     * 批量删除员工id带删除员工经历
     */
    void deleteByEmpIds(List<Integer> empIds);
}
