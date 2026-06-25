package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.idt.IdtIdentificationTask;
import com.pub.supervision.common.result.PageResult;
import org.springframework.web.multipart.MultipartFile;

public interface IdentificationTaskService extends IService<IdtIdentificationTask> {
    PageResult<IdtIdentificationTask> page(String taskName, Integer status, Integer taskType, int pageNum, int pageSize);
    IdtIdentificationTask getById(Long id);
    void create(IdtIdentificationTask task);
    void update(IdtIdentificationTask task);
    void delete(Long id);
    void assign(Long id, Long assigneeId);
    void start(Long id);
    void submitResult(Long id, String result);
    void review(Long id, Integer action, String opinion);
    void returnTask(Long id, String reason);
    void archive(Long id);
}
