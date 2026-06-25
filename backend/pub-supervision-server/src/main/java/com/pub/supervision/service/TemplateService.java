package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.opi.OpiReportTemplate;
import com.pub.supervision.common.result.PageResult;

public interface TemplateService extends IService<OpiReportTemplate> {
    PageResult<OpiReportTemplate> page(String templateName, int pageNum, int pageSize);
    void create(OpiReportTemplate template);
    void update(OpiReportTemplate template);
    void delete(Long id);
}
