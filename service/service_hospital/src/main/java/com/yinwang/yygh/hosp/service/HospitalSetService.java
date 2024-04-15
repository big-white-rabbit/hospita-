package com.yinwang.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinwang.yygh.model.hosp.HospitalSet;
import org.apache.ibatis.annotations.Mapper;

public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);
}
