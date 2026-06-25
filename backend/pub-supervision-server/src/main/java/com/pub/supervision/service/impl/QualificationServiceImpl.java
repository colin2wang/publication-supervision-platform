package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.circ.CircQualification;
import com.pub.supervision.mapper.CircQualificationMapper;
import com.pub.supervision.service.QualificationService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QualificationServiceImpl extends ServiceImpl<CircQualificationMapper, CircQualification> implements QualificationService {
    @Override public List<CircQualification> listByMerchant(Long merchantId) { LambdaQueryWrapper<CircQualification> w = new LambdaQueryWrapper<>(); w.eq(CircQualification::getMerchantId, merchantId); return list(w); }
    @Override public void create(CircQualification q) { q.setStatus(0); save(q); }
    @Override public void verify(Long id, Integer result) { CircQualification q = getById(id); q.setStatus(result); q.setVerifiedBy(SecurityUtils.getCurrentUserId()); q.setVerifiedAt(LocalDateTime.now()); updateById(q); }
    @Override public void batchVerify(List<Long> ids, Integer result) { for (Long id : ids) verify(id, result); }
}
