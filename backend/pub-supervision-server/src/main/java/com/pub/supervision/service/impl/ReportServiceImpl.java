package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.opi.OpiReport;
import com.pub.supervision.mapper.OpiReportMapper;
import com.pub.supervision.service.ReportService;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl extends ServiceImpl<OpiReportMapper, OpiReport> implements ReportService {
    @Override public PageResult<OpiReport> page(String reportTitle, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<OpiReport> w = new LambdaQueryWrapper<>();
        w.like(reportTitle != null && !reportTitle.isEmpty(), OpiReport::getReportTitle, reportTitle)
         .eq(status != null, OpiReport::getStatus, status).orderByDesc(OpiReport::getCreatedAt);
        Page<OpiReport> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public OpiReport getById(Long id) { OpiReport r = super.getById(id); if (r == null) throw new BusinessException(ResultCode.NOT_FOUND); return r; }
    @Override public void generate(OpiReport report) { report.setCreatorId(SecurityUtils.getCurrentUserId()); report.setStatus(0); report.setGeneratedBy(1); save(report); }
    @Override public void review(Long id, Integer action, String remark) { OpiReport r = getById(id); r.setStatus(action == 1 ? 2 : 0); r.setReviewerId(SecurityUtils.getCurrentUserId()); r.setReviewRemark(remark); updateById(r); }
    @Override public void publish(Long id) { OpiReport r = getById(id); r.setStatus(2); updateById(r); }
}
