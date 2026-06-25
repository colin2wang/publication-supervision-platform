package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.circ.CircAlert;
import com.pub.supervision.entity.circ.CircAlertHandle;
import com.pub.supervision.common.result.PageResult;
import java.util.List;

public interface AlertService extends IService<CircAlert> {
    PageResult<CircAlert> page(Integer alertType, Integer alertLevel, Integer status, int pageNum, int pageSize);
    CircAlert getById(Long id);
    void verify(Long id);
    void handle(Long id, CircAlertHandle handle);
    void archive(Long id);
    List<CircAlertHandle> getHandles(Long alertId);
}
