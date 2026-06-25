package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.circ.CircMerchant;
import com.pub.supervision.common.result.PageResult;

public interface MerchantService extends IService<CircMerchant> {
    PageResult<CircMerchant> page(String merchantName, Integer riskLevel, Integer status, int pageNum, int pageSize);
    CircMerchant getById(Long id);
    void create(CircMerchant merchant);
    void update(CircMerchant merchant);
    void delete(Long id);
    Integer calculateRiskScore(Long id);
}
