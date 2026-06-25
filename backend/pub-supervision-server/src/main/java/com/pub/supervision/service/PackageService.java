package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.circ.CircPackage;
import com.pub.supervision.common.result.PageResult;
import java.util.List;
import java.util.Map;

public interface PackageService extends IService<CircPackage> {
    PageResult<CircPackage> page(String packageCode, Long merchantId, Integer status, int pageNum, int pageSize);
    CircPackage getById(Long id);
    void create(CircPackage pkg);
    List<Map<String, Object>> getTracking(Long id);
    void sync(Long id);
    void intercept(Long id);
}
