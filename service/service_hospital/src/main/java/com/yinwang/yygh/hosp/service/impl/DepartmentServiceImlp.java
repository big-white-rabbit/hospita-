package com.yinwang.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yinwang.yygh.hosp.repository.DepartmentRepository;
import com.yinwang.yygh.hosp.service.DepartmentService;
import com.yinwang.yygh.model.hosp.Department;
import com.yinwang.yygh.vo.hosp.DepartmentQueryVo;
import com.yinwang.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImlp implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 上传科室接口
     * @param paramMap
     */
    @Override
    public void save(Map<String, Object> paramMap) {
        //将paramMap转为Department对象
        String paramMapString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(paramMapString, Department.class);

        //根据医院编号、科室编号查询
        Department departmentExists = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(),
                department.getDepcode());

        //如果存在
        if (departmentExists != null) {
            departmentExists.setUpdateTime(new Date());
            departmentExists.setIsDeleted(0);
            departmentRepository.save(departmentExists);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }


    /**
     * 查询科室接口
     * @param page
     * @param limit
     * @param departmentQueryVo
     * @return
     */
    @Override
    public Page<Department> findPageDepartment(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo) {
        //将Vo对象转为department对象并初始化
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);

        //创建pageable对象，设置当前页和每页记录数
        Pageable pageable = PageRequest.of(page - 1, limit);
        //创建example对象，传递pageable对象
        ExampleMatcher matcher = ExampleMatcher.matching().
                withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);

        Example<Department> example = Example.of(department, matcher);

        Page<Department> departmentPage = departmentRepository.findAll(example, pageable);
        return departmentPage;
    }

    /**
     * 删除科室接口
     * @param hoscode
     * @param depcode
     */
    @Override
    public void remove(String hoscode, String depcode) {
        //根据医院编号、科室编号查询科室信息
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            //如果存在，删除
            departmentRepository.deleteById(department.getId());
        }
    }

    /**
     * 查询医院所有科室列表
     * @param hoscode
     * @return
     */
    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        List<DepartmentVo> result = new ArrayList<>();
        //根据hoscode查询所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example example = Example.of(departmentQuery);
        //所有科室信息
        List<Department> deptList = departmentRepository.findAll(example);

        //根据大科室编号分组，获取大科室下的子科室
        Map<String, List<Department>> deptMap = deptList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        //遍历deptMap集合
        for (Map.Entry<String, List<Department>> entry : deptMap.entrySet()) {
            //大科室编号
            String bigCode = entry.getKey();
            //大科室对应的子数据
            List<Department> list = entry.getValue();
            //封装大科室
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDepcode(bigCode);
            departmentVo.setDepname(list.get(0).getBigname());
            //封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for (Department department : list) {
                DepartmentVo departmentVo1 = new DepartmentVo();
                departmentVo1.setDepcode(department.getDepcode());
                departmentVo1.setDepname(department.getDepname());
                children.add(departmentVo1);
            }
            //将小科室赋给大科室
            departmentVo.setChildren(children);
            result.add(departmentVo);
        }

        return result;
    }

    //根据医院编号、科室编号查询科室名称
    @Override
    public String getDepName(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            return department.getDepname();
        }
        return null;
    }
}
