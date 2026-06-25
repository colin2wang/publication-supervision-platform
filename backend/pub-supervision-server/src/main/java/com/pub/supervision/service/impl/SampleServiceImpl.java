package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.idt.IdtIllegalSample;
import com.pub.supervision.mapper.IdtIllegalSampleMapper;
import com.pub.supervision.service.SampleService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SampleServiceImpl extends ServiceImpl<IdtIllegalSampleMapper, IdtIllegalSample> implements SampleService {
    @Override public PageResult<IdtIllegalSample> page(String sampleName, Integer illegalType, int pageNum, int pageSize) {
        LambdaQueryWrapper<IdtIllegalSample> w = new LambdaQueryWrapper<>();
        w.like(sampleName != null && !sampleName.isEmpty(), IdtIllegalSample::getSampleName, sampleName)
         .eq(illegalType != null, IdtIllegalSample::getIllegalType, illegalType).orderByDesc(IdtIllegalSample::getCreatedAt);
        Page<IdtIllegalSample> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public IdtIllegalSample getById(Long id) { IdtIllegalSample s = super.getById(id); if (s == null) throw new BusinessException(ResultCode.NOT_FOUND); return s; }
    @Override public void create(IdtIllegalSample sample) { save(sample); }
    @Override public void update(IdtIllegalSample sample) { updateById(sample); }
    @Override public void delete(Long id) { removeById(id); }
    @Override public Map<String, Object> extractFeatures(Long id) { Map<String, Object> r = new HashMap<>(); r.put("sampleId", id); r.put("status", "extracted"); return r; }
    @Override public List<Map<String, Object>> compare(Long id, Double threshold) { List<Map<String, Object>> r = new ArrayList<>(); Map<String, Object> item = new HashMap<>(); item.put("sampleId", id); item.put("score", 0.95); r.add(item); return r; }
    @Override public Map<String, Object> trace(Long id) { Map<String, Object> r = new HashMap<>(); r.put("sampleId", id); r.put("chain", "source -> publisher -> distributor"); return r; }
}
