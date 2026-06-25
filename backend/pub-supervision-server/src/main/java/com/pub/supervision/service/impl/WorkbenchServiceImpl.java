package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pub.supervision.entity.idt.IdtIdentificationTask;
import com.pub.supervision.entity.circ.CircAlert;
import com.pub.supervision.mapper.IdtIdentificationTaskMapper;
import com.pub.supervision.mapper.CircAlertMapper;
import com.pub.supervision.service.WorkbenchService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WorkbenchServiceImpl implements WorkbenchService {
    private final IdtIdentificationTaskMapper taskMapper;
    private final CircAlertMapper alertMapper;
    public WorkbenchServiceImpl(IdtIdentificationTaskMapper taskMapper, CircAlertMapper alertMapper) { this.taskMapper = taskMapper; this.alertMapper = alertMapper; }

    @Override
    public Map<String, Object> getStats(Long userId) {
        Map<String, Object> stats = new HashMap<>();
        LambdaQueryWrapper<IdtIdentificationTask> tw = new LambdaQueryWrapper<>();
        tw.eq(IdtIdentificationTask::getAssigneeId, userId);
        stats.put("myTasks", taskMapper.selectCount(tw));
        LambdaQueryWrapper<CircAlert> aw = new LambdaQueryWrapper<>();
        aw.eq(CircAlert::getStatus, 0);
        stats.put("pendingAlerts", alertMapper.selectCount(aw));
        return stats;
    }

    @Override
    public List<Map<String, Object>> getTodoList(Long userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        LambdaQueryWrapper<IdtIdentificationTask> w = new LambdaQueryWrapper<>();
        w.eq(IdtIdentificationTask::getAssigneeId, userId).in(IdtIdentificationTask::getStatus, 1, 2).orderByDesc(IdtIdentificationTask::getCreatedAt);
        for (IdtIdentificationTask t : taskMapper.selectList(w)) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", t.getId()); item.put("title", t.getTaskName()); item.put("type", "task"); item.put("status", t.getStatus());
            list.add(item);
        }
        return list;
    }
}
