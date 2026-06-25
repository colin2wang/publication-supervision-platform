package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.circ.CircMerchant;
import com.pub.supervision.mapper.CircMerchantMapper;
import com.pub.supervision.service.MerchantService;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl extends ServiceImpl<CircMerchantMapper, CircMerchant> implements MerchantService {
    @Override public PageResult<CircMerchant> page(String merchantName, Integer riskLevel, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<CircMerchant> w = new LambdaQueryWrapper<>();
        w.like(merchantName != null && !merchantName.isEmpty(), CircMerchant::getMerchantName, merchantName)
         .eq(riskLevel != null, CircMerchant::getRiskLevel, riskLevel)
         .eq(status != null, CircMerchant::getStatus, status).orderByDesc(CircMerchant::getCreatedAt);
        Page<CircMerchant> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public CircMerchant getById(Long id) { CircMerchant m = super.getById(id); if (m == null) throw new BusinessException(ResultCode.NOT_FOUND); return m; }
    @Override public void create(CircMerchant m) { save(m); }
    @Override public void update(CircMerchant m) { updateById(m); }
    @Override public void delete(Long id) { removeById(id); }
    @Override public Integer calculateRiskScore(Long id) {
        CircMerchant m = getById(id); int score = 0;
        if (m.getRiskScore() != null) score = m.getRiskScore();
        m.setRiskScore(score);
        m.setRiskLevel(score >= 70 ? 3 : score >= 40 ? 2 : 1);
        updateById(m); return score;
    }
}
