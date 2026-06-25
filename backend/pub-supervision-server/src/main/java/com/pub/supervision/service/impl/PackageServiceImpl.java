package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.circ.CircPackage;
import com.pub.supervision.entity.circ.CircTrackingLog;
import com.pub.supervision.mapper.CircPackageMapper;
import com.pub.supervision.mapper.CircTrackingLogMapper;
import com.pub.supervision.service.PackageService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PackageServiceImpl extends ServiceImpl<CircPackageMapper, CircPackage> implements PackageService {
    private final CircTrackingLogMapper trackingLogMapper;
    public PackageServiceImpl(CircTrackingLogMapper trackingLogMapper) { this.trackingLogMapper = trackingLogMapper; }

    @Override public PageResult<CircPackage> page(String packageCode, Long merchantId, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<CircPackage> w = new LambdaQueryWrapper<>();
        w.like(packageCode != null && !packageCode.isEmpty(), CircPackage::getPackageCode, packageCode)
         .eq(merchantId != null, CircPackage::getMerchantId, merchantId)
         .eq(status != null, CircPackage::getStatus, status).orderByDesc(CircPackage::getCreatedAt);
        Page<CircPackage> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public CircPackage getById(Long id) { CircPackage pkg = super.getById(id); if (pkg == null) throw new BusinessException(ResultCode.NOT_FOUND); return pkg; }
    @Override public void create(CircPackage pkg) { save(pkg); }
    @Override public List<Map<String, Object>> getTracking(Long id) {
        LambdaQueryWrapper<CircTrackingLog> w = new LambdaQueryWrapper<>();
        w.eq(CircTrackingLog::getPackageId, id).orderByAsc(CircTrackingLog::getEventTime);
        List<Map<String, Object>> result = new ArrayList<>();
        for (CircTrackingLog log : trackingLogMapper.selectList(w)) {
            Map<String, Object> item = new HashMap<>();
            item.put("location", log.getLocation()); item.put("status", log.getStatus()); item.put("eventTime", log.getEventTime());
            result.add(item);
        }
        return result;
    }
    @Override public void sync(Long id) { CircPackage pkg = getById(id); pkg.setStatus(1); updateById(pkg); }
    @Override public void intercept(Long id) { CircPackage pkg = getById(id); pkg.setStatus(9); updateById(pkg); }
}
