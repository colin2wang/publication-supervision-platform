package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.opi.OpiCollectionTask;
import com.pub.supervision.service.CollectionTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "采集任务管理")
@RestController
@RequestMapping("/api/v1/opinion/collection-tasks")
public class CollectionTaskController {
    private final CollectionTaskService taskService;
    public CollectionTaskController(CollectionTaskService taskService) { this.taskService = taskService; }

    @Operation(summary = "任务列表")
    @GetMapping
    public R<PageResult<OpiCollectionTask>> list(@RequestParam(required = false) String taskName, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(taskService.page(taskName, status, pageNum, pageSize));
    }
    @Operation(summary = "任务详情")
    @GetMapping("/{id}")
    public R<OpiCollectionTask> getById(@PathVariable Long id) { return R.ok(taskService.getById(id)); }
    @Operation(summary = "创建任务")
    @PostMapping
    public R<Void> create(@RequestBody OpiCollectionTask task) { taskService.create(task); return R.ok(); }
    @Operation(summary = "更新任务")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody OpiCollectionTask task) { task.setId(id); taskService.update(task); return R.ok(); }
    @Operation(summary = "删除任务")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { taskService.delete(id); return R.ok(); }
    @Operation(summary = "执行任务")
    @PostMapping("/{id}/execute")
    public R<Void> execute(@PathVariable Long id) { taskService.execute(id); return R.ok(); }
    @Operation(summary = "变更状态")
    @PostMapping("/{id}/status")
    public R<Void> changeStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) { taskService.changeStatus(id, params.get("status")); return R.ok(); }
}
