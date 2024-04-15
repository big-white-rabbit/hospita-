package com.yinwang.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinwang.yygh.common.result.Result;
import com.yinwang.yygh.model.cmn.Dict;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    //根据数据id查询子数据列表
    List<Dict> findChildData(Long id);

    //导出数据字典
    void exportDictData(HttpServletResponse response);

    //导入数据字典
    void importDictData(MultipartFile file);

    //根据dictCode和value查询
    String getDictName(String dictCode, String value);
}
