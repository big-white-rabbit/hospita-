package com.yinwang.yygh.hosp.controller;


import com.yinwang.yygh.common.result.Result;
import com.yinwang.yygh.hosp.service.HospitalService;
import com.yinwang.yygh.model.hosp.Hospital;
import com.yinwang.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hosp/hospital")
@CrossOrigin
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    /**
     * 医院列表分页查询
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @GetMapping("list/{page}/{limit}")
    public Result listHospital(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModel = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(pageModel);
    }
}
