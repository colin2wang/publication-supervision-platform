package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.circ.CircQualification;
import java.util.List;

public interface QualificationService extends IService<CircQualification> {
    List<CircQualification> listByMerchant(Long merchantId);
    void create(CircQualification qual);
    void verify(Long id, Integer result);
    void batchVerify(List<Long> ids, Integer result);
}
