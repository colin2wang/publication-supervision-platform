package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.opi.OpiCollectionTask;
import com.pub.supervision.mapper.OpiCollectionTaskMapper;
import com.pub.supervision.service.CollectionTaskService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CollectionTaskServiceImpl extends ServiceImpl<OpiCollectionTaskMapper, OpiCollectionTask> implements CollectionTaskService {
    @Override public PageResult<OpiCollectionTask> page(String taskName, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<OpiCollectionTask> w = new LambdaQueryWrapper<>();
        w.like(taskName != null && !taskName.isEmpty(), OpiCollectionTask::getTaskName, taskName)
         .eq(status != null, OpiCollectionTask::getStatus, status).orderByDesc(OpiCollectionTask::getCreatedAt);
        Page<OpiCollectionTask> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public OpiCollectionTask getById(Long id) { OpiCollectionTask t = super.getById(id); if (t == null) throw new BusinessException(ResultCode.NOT_FOUND); return t; }
    @Override public void create(OpiCollectionTask task) { task.setCreatorId(SecurityUtils.getCurrentUserId()); task.setStatus(0); save(task); }
    @Override public void update(OpiCollectionTask task) { updateById(task); }
    @Override public void delete(Long id) { removeById(id); }
    @Override public void execute(Long id) { OpiCollectionTask t = getById(id); t.setStatus(1); t.setLastRunAt(LocalDateTime.now()); updateById(t); }
    @Override public void changeStatus(Long id, Integer status) { OpiCollectionTask t = getById(id); t.setStatus(status); updateById(t); }
}
