package com.yinwang.yygh.hosp.service;


import com.yinwang.yygh.model.hosp.Hospital;
import com.yinwang.yygh.vo.hosp.HospitalQueryVo;
import com.yinwang.yygh.vo.hosp.HospitalSetQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface HospitalService {
    void save(Map<String, Object> paramMap);

    Hospital getByHoscode(String hoscode);

    //医院列表分页查询
    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);
}
