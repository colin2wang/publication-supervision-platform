package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.opi.OpiCollectionTask;
import com.pub.supervision.common.result.PageResult;

public interface CollectionTaskService extends IService<OpiCollectionTask> {
    PageResult<OpiCollectionTask> page(String taskName, Integer status, int pageNum, int pageSize);
    OpiCollectionTask getById(Long id);
    void create(OpiCollectionTask task);
    void update(OpiCollectionTask task);
    void delete(Long id);
    void execute(Long id);
    void changeStatus(Long id, Integer status);
}
