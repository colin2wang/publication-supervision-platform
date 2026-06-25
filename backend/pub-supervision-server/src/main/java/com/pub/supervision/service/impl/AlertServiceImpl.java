package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.circ.CircAlert;
import com.pub.supervision.entity.circ.CircAlertHandle;
import com.pub.supervision.mapper.CircAlertMapper;
import com.pub.supervision.mapper.CircAlertHandleMapper;
import com.pub.supervision.service.AlertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AlertServiceImpl extends ServiceImpl<CircAlertMapper, CircAlert> implements AlertService {
    private final CircAlertHandleMapper alertHandleMapper;
    public AlertServiceImpl(CircAlertHandleMapper alertHandleMapper) { this.alertHandleMapper = alertHandleMapper; }

    @Override public PageResult<CircAlert> page(Integer alertType, Integer alertLevel, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<CircAlert> w = new LambdaQueryWrapper<>();
        w.eq(alertType != null, CircAlert::getAlertType, alertType)
         .eq(alertLevel != null, CircAlert::getAlertLevel, alertLevel)
         .eq(status != null, CircAlert::getStatus, status).orderByDesc(CircAlert::getCreatedAt);
        Page<CircAlert> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public CircAlert getById(Long id) { CircAlert a = super.getById(id); if (a == null) throw new BusinessException(ResultCode.NOT_FOUND); return a; }
    @Override public void verify(Long id) { CircAlert a = getById(id); a.setStatus(1); updateById(a); }
    @Override @Transactional public void handle(Long id, CircAlertHandle handle) {
        CircAlert a = getById(id); a.setStatus(2); a.setHandlerId(SecurityUtils.getCurrentUserId()); a.setHandleResult(handle.getHandleResult()); updateById(a);
        handle.setAlertId(id); handle.setHandlerId(SecurityUtils.getCurrentUserId()); alertHandleMapper.insert(handle);
    }
    @Override public void archive(Long id) { CircAlert a = getById(id); a.setStatus(3); updateById(a); }
    @Override public List<CircAlertHandle> getHandles(Long alertId) { LambdaQueryWrapper<CircAlertHandle> w = new LambdaQueryWrapper<>(); w.eq(CircAlertHandle::getAlertId, alertId); return alertHandleMapper.selectList(w); }
}
