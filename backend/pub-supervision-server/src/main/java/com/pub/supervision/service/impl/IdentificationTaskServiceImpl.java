package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.idt.IdtIdentificationTask;
import com.pub.supervision.entity.idt.IdtReviewLog;
import com.pub.supervision.mapper.IdtIdentificationTaskMapper;
import com.pub.supervision.mapper.IdtReviewLogMapper;
import com.pub.supervision.service.IdentificationTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class IdentificationTaskServiceImpl extends ServiceImpl<IdtIdentificationTaskMapper, IdtIdentificationTask> implements IdentificationTaskService {
    private final IdtReviewLogMapper reviewLogMapper;
    public IdentificationTaskServiceImpl(IdtReviewLogMapper reviewLogMapper) { this.reviewLogMapper = reviewLogMapper; }

    @Override public PageResult<IdtIdentificationTask> page(String taskName, Integer status, Integer taskType, int pageNum, int pageSize) {
        LambdaQueryWrapper<IdtIdentificationTask> w = new LambdaQueryWrapper<>();
        w.like(taskName != null && !taskName.isEmpty(), IdtIdentificationTask::getTaskName, taskName)
         .eq(status != null, IdtIdentificationTask::getStatus, status)
         .eq(taskType != null, IdtIdentificationTask::getTaskType, taskType)
         .orderByDesc(IdtIdentificationTask::getCreatedAt);
        Page<IdtIdentificationTask> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public IdtIdentificationTask getById(Long id) { IdtIdentificationTask t = super.getById(id); if (t == null) throw new BusinessException(ResultCode.NOT_FOUND); return t; }
    @Override public void create(IdtIdentificationTask task) { task.setCreatorId(SecurityUtils.getCurrentUserId()); task.setStatus(0); save(task); }
    @Override public void update(IdtIdentificationTask task) { updateById(task); }
    @Override public void delete(Long id) { removeById(id); }
    @Override public void assign(Long id, Long assigneeId) { IdtIdentificationTask t = getById(id); t.setAssigneeId(assigneeId); t.setStatus(1); updateById(t); }
    @Override public void start(Long id) { IdtIdentificationTask t = getById(id); t.setStatus(2); t.setStartedAt(LocalDateTime.now()); updateById(t); }
    @Override public void submitResult(Long id, String result) { IdtIdentificationTask t = getById(id); t.setResult(result); t.setStatus(3); updateById(t); }
    @Override @Transactional public void review(Long id, Integer action, String opinion) {
        IdtIdentificationTask t = getById(id); t.setStatus(action == 1 ? 4 : 1); updateById(t);
        IdtReviewLog log = new IdtReviewLog(); log.setTaskId(id); log.setReviewerId(SecurityUtils.getCurrentUserId()); log.setAction(action); log.setOpinion(opinion); reviewLogMapper.insert(log);
    }
    @Override public void returnTask(Long id, String reason) { IdtIdentificationTask t = getById(id); t.setStatus(1); t.setRemark(reason); updateById(t); }
    @Override public void archive(Long id) { IdtIdentificationTask t = getById(id); t.setStatus(5); t.setCompletedAt(LocalDateTime.now()); updateById(t); }
}
