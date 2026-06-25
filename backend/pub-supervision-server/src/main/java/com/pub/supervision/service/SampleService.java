package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.idt.IdtIllegalSample;
import com.pub.supervision.common.result.PageResult;

public interface SampleService extends IService<IdtIllegalSample> {
    PageResult<IdtIllegalSample> page(String sampleName, Integer illegalType, int pageNum, int pageSize);
    IdtIllegalSample getById(Long id);
    void create(IdtIllegalSample sample);
    void update(IdtIllegalSample sample);
    void delete(Long id);
    java.util.Map<String, Object> extractFeatures(Long id);
    java.util.List<java.util.Map<String, Object>> compare(Long id, Double threshold);
    java.util.Map<String, Object> trace(Long id);
}
