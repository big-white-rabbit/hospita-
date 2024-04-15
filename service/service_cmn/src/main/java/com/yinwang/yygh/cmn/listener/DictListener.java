package com.yinwang.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yinwang.yygh.cmn.mapper.DictMapper;
import com.yinwang.yygh.model.cmn.Dict;
import com.yinwang.yygh.vo.cmn.DictEeVo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Map;

public class DictListener extends AnalysisEventListener<DictEeVo> {
    private DictMapper dictMapper;

    public DictListener() {}

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
