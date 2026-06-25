package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.opi.OpiReportTemplate;
import com.pub.supervision.mapper.OpiReportTemplateMapper;
import com.pub.supervision.service.TemplateService;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl extends ServiceImpl<OpiReportTemplateMapper, OpiReportTemplate> implements TemplateService {
    @Override public PageResult<OpiReportTemplate> page(String templateName, int pageNum, int pageSize) {
        LambdaQueryWrapper<OpiReportTemplate> w = new LambdaQueryWrapper<>();
        w.like(templateName != null && !templateName.isEmpty(), OpiReportTemplate::getTemplateName, templateName).orderByDesc(OpiReportTemplate::getCreatedAt);
        Page<OpiReportTemplate> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public void create(OpiReportTemplate t) { save(t); }
    @Override public void update(OpiReportTemplate t) { updateById(t); }
    @Override public void delete(Long id) { removeById(id); }
}
