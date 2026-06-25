package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.idt.IdtIdentificationTask;
import com.pub.supervision.service.IdentificationTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "鉴定任务管理")
@RestController
@RequestMapping("/api/v1/identification/tasks")
public class TaskController {
    private final IdentificationTaskService taskService;
    public TaskController(IdentificationTaskService taskService) { this.taskService = taskService; }

    @Operation(summary = "任务列表")
    @GetMapping
    public R<PageResult<IdtIdentificationTask>> list(@RequestParam(required = false) String taskName, @RequestParam(required = false) Integer status, @RequestParam(required = false) Integer taskType, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(taskService.page(taskName, status, taskType, pageNum, pageSize));
    }
    @Operation(summary = "任务详情")
    @GetMapping("/{id}")
    public R<IdtIdentificationTask> getById(@PathVariable Long id) { return R.ok(taskService.getById(id)); }
    @Operation(summary = "创建任务")
    @PostMapping
    public R<Void> create(@RequestBody IdtIdentificationTask task) { taskService.create(task); return R.ok(); }
    @Operation(summary = "更新任务")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody IdtIdentificationTask task) { task.setId(id); taskService.update(task); return R.ok(); }
    @Operation(summary = "删除任务")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { taskService.delete(id); return R.ok(); }
    @Operation(summary = "分配任务")
    @PostMapping("/{id}/assign")
    public R<Void> assign(@PathVariable Long id, @RequestBody Map<String, Long> params) { taskService.assign(id, params.get("assigneeId")); return R.ok(); }
    @Operation(summary = "开始任务")
    @PostMapping("/{id}/start")
    public R<Void> start(@PathVariable Long id) { taskService.start(id); return R.ok(); }
    @Operation(summary = "提交结果")
    @PostMapping("/{id}/submit")
    public R<Void> submit(@PathVariable Long id, @RequestBody Map<String, String> params) { taskService.submitResult(id, params.get("result")); return R.ok(); }
    @Operation(summary = "审核任务")
    @PostMapping("/{id}/review")
    public R<Void> review(@PathVariable Long id, @RequestBody Map<String, Object> params) { taskService.review(id, (Integer) params.get("action"), (String) params.get("opinion")); return R.ok(); }
    @Operation(summary = "退回任务")
    @PostMapping("/{id}/return")
    public R<Void> returnTask(@PathVariable Long id, @RequestBody Map<String, String> params) { taskService.returnTask(id, params.get("reason")); return R.ok(); }
    @Operation(summary = "归档任务")
    @PostMapping("/{id}/archive")
    public R<Void> archive(@PathVariable Long id) { taskService.archive(id); return R.ok(); }
}
