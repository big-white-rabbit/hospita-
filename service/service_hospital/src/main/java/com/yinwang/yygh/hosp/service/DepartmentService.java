package com.yinwang.yygh.hosp.service;

import com.yinwang.yygh.model.hosp.Department;
import com.yinwang.yygh.vo.hosp.DepartmentQueryVo;
import com.yinwang.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    //上传科室接口
    void save(Map<String, Object> paramMap);

    //查询科室接口
    Page<Department> findPageDepartment(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo);

    //删除科室接口
    void remove(String hoscode, String depcode);

    //查询医院所有科室列表
    List<DepartmentVo> findDeptTree(String hoscode);

    //根据医院编号、科室编号查询科室名称
    String getDepName(String hoscode, String depcode);
}
