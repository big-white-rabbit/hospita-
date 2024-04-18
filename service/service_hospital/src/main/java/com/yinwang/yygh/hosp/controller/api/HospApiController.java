package com.yinwang.yygh.hosp.controller.api;

import com.yinwang.yygh.common.result.Result;
import com.yinwang.yygh.hosp.service.DepartmentService;
import com.yinwang.yygh.hosp.service.HospitalService;
import com.yinwang.yygh.model.hosp.Hospital;
import com.yinwang.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp/hospital")
public class HospApiController {
    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询医院列表接口
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @ApiOperation(value = "查询医院列表接口")
    @GetMapping("findHospList/{page}/{limit}")
    public Result findHospList(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> hospPage = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(hospPage);
    }

    /**
     * 根据医院名称查询
     * @param hosname
     * @return
     */
    @ApiOperation(value = "根据医院名称查询")
    @GetMapping("findByHosName/{hosname}")
    public Result findByHosName(@PathVariable String hosname) {
        List<Hospital> list =  hospitalService.findByHosname(hosname);
        return Result.ok(list);
    }

    /**
     * 根据医院编号获取所有科室信息
     * @param hoscode
     * @return
     */
    @ApiOperation(value = "根据医院编号获取所有科室信息")
    @GetMapping("department/{hoscode}")
    public Result index(@PathVariable String hoscode) {
        return Result.ok(departmentService.findDeptTree(hoscode));
    }

    @ApiOperation(value = "根据医院编号获取预约挂号详情")
    @GetMapping("findHospDetail/{hoscode}")
    public Result item(@PathVariable String hoscode) {
        Map<String, Object> map = hospitalService.item(hoscode);
        return Result.ok(map);
    }
}
