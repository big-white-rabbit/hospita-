package com.yinwang.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.jdbc.StringUtils;
import com.yinwang.yygh.common.exception.YyghException;
import com.yinwang.yygh.common.helper.HttpRequestHelper;
import com.yinwang.yygh.common.result.Result;
import com.yinwang.yygh.common.utils.MD5;
import com.yinwang.yygh.hosp.service.HospitalSetService;
import com.yinwang.yygh.model.hosp.HospitalSet;
import com.yinwang.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    /**
     * 查询医院设置表所有信息
     * null
     * @return Result
     */
    @GetMapping("findAll")
    @ApiOperation("查询医院设置表所有信息")
    public Result findAllHospitalSet() {
        // 调用service方法
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    /**
     * 根据id删除
     * @param id
     * @return Result
     */
    @DeleteMapping("{id}")
    @ApiOperation("根据id逻辑删除")
    public Result removeHospitalSet(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 分页查询医院设置
     * @param current
     * @param limit
     * @param hospitalSetQueryVo
     * @return
     */
    @PostMapping("findPage/{current}/{limit}")
    @ApiOperation("分页查询医院设置")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> page = new Page<>(current, limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isNullOrEmpty(hosname)) {
            queryWrapper.like("hosname", hosname);
        }
        if (!StringUtils.isNullOrEmpty(hoscode)) {
            queryWrapper.eq("hoscode", hoscode);
        }

        Page<HospitalSet> set = hospitalSetService.page(page, queryWrapper);

        return Result.ok(set);
    }

    /**
     * 添加医院设置
     * @param hospitalSet
     * @return
     */
    @PostMapping("saveHospitalSet")
    @ApiOperation("添加医院设置")
    public Result saveHospitalSet(@RequestBody() HospitalSet hospitalSet) {
        hospitalSet.setStatus(1);
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 根据id查询医院设置
     * @param id
     * @return
     */
    @GetMapping("getHospSet/{id}")
    @ApiOperation("根据id查询医院设置")
    public Result getHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 根据id更新医院设置
     * @param hospitalSet
     * @return
     */
    @PostMapping("updateHospSet")
    @ApiOperation("根据id更新医院设置")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 批量删除医院设置
     * @param hospitalSets
     * @return
     */
    @DeleteMapping("batchRemoveHospSet")
    @ApiOperation("批量删除医院设置")
    public Result batchRemoveHospSet(@RequestBody List<Long> hospitalSets) {
        boolean flag = hospitalSetService.removeByIds(hospitalSets);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 上锁、解锁
     * @param id
     * @param status
     * @return
     */
    @PutMapping("lockHospSet/{id}/{status}")
    public Result lockHospSet(@PathVariable long id, @PathVariable int status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送signKey、hoscode
        return Result.ok();
    }
}
