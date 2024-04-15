package com.yinwang.yygh.hosp.controller.api;

import com.yinwang.yygh.common.exception.YyghException;
import com.yinwang.yygh.common.helper.HttpRequestHelper;
import com.yinwang.yygh.common.result.Result;
import com.yinwang.yygh.common.result.ResultCodeEnum;
import com.yinwang.yygh.common.utils.MD5;
import com.yinwang.yygh.hosp.service.DepartmentService;
import com.yinwang.yygh.hosp.service.HospitalService;
import com.yinwang.yygh.hosp.service.HospitalSetService;
import com.yinwang.yygh.hosp.service.ScheduleService;
import com.yinwang.yygh.model.hosp.Department;
import com.yinwang.yygh.model.hosp.Hospital;
import com.yinwang.yygh.model.hosp.Schedule;
import com.yinwang.yygh.vo.hosp.DepartmentQueryVo;
import com.yinwang.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //删除排班接口
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request) {
        //从医院管理接受请求数据
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //获取科室编号
        String hosScheduleId = (String) paramMap.get("hosScheduleId");

        //获取签名
        String sign = (String) paramMap.get("sign");
        String signedKey = hospitalSetService.getSignKey(hoscode);
        String signedKeyMD5 = MD5.encrypt(signedKey);
        //判断签名是否一致
        if (!sign.equals(signedKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove(hoscode, hosScheduleId);
        return Result.ok();
    }

    /**
     * 查询排班接口
     * @param request
     * @return
     */
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        //从医院管理接受请求数据
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //获取科室编号
        String depcode = (String) paramMap.get("depcode");

        //获取签名
        String sign = (String) paramMap.get("sign");
        String signedKey = hospitalSetService.getSignKey(hoscode);
        String signedKeyMD5 = MD5.encrypt(signedKey);
        //判断签名是否一致
        if (!sign.equals(signedKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);

        //获取医院编号、当前页和页大小
        Integer page = StringUtils.isEmpty((String) paramMap.get("page")) ?
                1 : Integer.parseInt((String)paramMap.get("page"));
        Integer limit = StringUtils.isEmpty((String) paramMap.get("limit")) ?
                1 : Integer.parseInt((String)paramMap.get("limit"));

        Page<Schedule> pageModel = scheduleService.findPageSchedule(page, limit, scheduleQueryVo);

        return Result.ok(pageModel);
    }

    /**
     * 上传排班接口
     * @param request
     * @return
     */
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        //从医院管理接受请求数据
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");

        //获取签名
        String sign = (String) paramMap.get("sign");
        String signedKey = hospitalSetService.getSignKey(hoscode);
        String signedKeyMD5 = MD5.encrypt(signedKey);
        //判断签名是否一致
        if (!sign.equals(signedKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.save(paramMap);
        return Result.ok();
    }

    /**
     * 删除科室接口
     * @param request
     * @return
     */
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        //从医院管理接受请求数据
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //获取科室编号
        String depcode = (String) paramMap.get("depcode");

        //获取签名
        String sign = (String) paramMap.get("sign");
        String signedKey = hospitalSetService.getSignKey(hoscode);
        String signedKeyMD5 = MD5.encrypt(signedKey);
        //判断签名是否一致
        if (!sign.equals(signedKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

    /**
     * 查询科室接口
     * @param request
     * @return
     */
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        //从医院管理接受请求数据
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");

        //获取签名
        String sign = (String) paramMap.get("sign");
        String signedKey = hospitalSetService.getSignKey(hoscode);
        String signedKeyMD5 = MD5.encrypt(signedKey);
        //判断签名是否一致
        if (!sign.equals(signedKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);

        //获取医院编号、当前页和页大小
        Integer page = StringUtils.isEmpty((String) paramMap.get("page")) ?
                1 : Integer.parseInt((String)paramMap.get("page"));
        Integer limit = StringUtils.isEmpty((String) paramMap.get("limit")) ?
                1 : Integer.parseInt((String)paramMap.get("limit"));

        Page<Department> pageModel = departmentService.findPageDepartment(page, limit, departmentQueryVo);

        return Result.ok(pageModel);
    }

    /**
     * 上传科室接口
     * @param request
     * @return
     */
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号
        String hoscode = (String)paramMap.get("hoscode");
        String hospSign = (String)paramMap.get("sign");

        String signedKey = hospitalSetService.getSignKey(hoscode);

        //将从数据库得到的签名加密
        String signedKeyMD5 = MD5.encrypt(signedKey);

        //判断签名是否一致
        if (!hospSign.equals(signedKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //调用service方法
        departmentService.save(paramMap);
        return Result.ok();
    }

    /**
     * 查询医院
     * @param request
     * @return
     */
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String)paramMap.get("hoscode");
        String hospSign = (String)paramMap.get("sign");

        String signedKey = hospitalSetService.getSignKey(hoscode);

        //将从数据库得到的签名加密
        String signedKeyMD5 = MD5.encrypt(signedKey);

        //判断签名是否一致
        if (!hospSign.equals(signedKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }


    //上传医院接口
    @PostMapping("/saveHospital")
    public Result saveHospital(HttpServletRequest request) {
        //获取传来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院系统传入的签名
        String hospSign = (String) paramMap.get("sign");

        //根据医院系统传入的编码查询数据库、签名
        String hoscode = (String) paramMap.get("hoscode");
        String signedKey = hospitalSetService.getSignKey(hoscode);

        //将从数据库得到的签名加密
        String signedKeyMD5 = MD5.encrypt(signedKey);

        //判断签名是否一致
        if (!hospSign.equals(signedKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);
        //调用service方法
        hospitalService.save(paramMap);
        return Result.ok();
    }

}
