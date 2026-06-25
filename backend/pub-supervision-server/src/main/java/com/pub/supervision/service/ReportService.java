package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.opi.OpiReport;
import com.pub.supervision.common.result.PageResult;

public interface ReportService extends IService<OpiReport> {
    PageResult<OpiReport> page(String reportTitle, Integer status, int pageNum, int pageSize);
    OpiReport getById(Long id);
    void generate(OpiReport report);
    void review(Long id, Integer action, String remark);
    void publish(Long id);
}
