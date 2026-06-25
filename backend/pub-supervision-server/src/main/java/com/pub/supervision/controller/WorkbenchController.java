package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.service.WorkbenchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "工作台")
@RestController
@RequestMapping("/api/v1/identification/workbench")
public class WorkbenchController {
    private final WorkbenchService workbenchService;
    public WorkbenchController(WorkbenchService workbenchService) { this.workbenchService = workbenchService; }

    @Operation(summary = "统计概览")
    @GetMapping("/stats")
    public R<Map<String, Object>> stats() { return R.ok(workbenchService.getStats(SecurityUtils.getCurrentUserId())); }

    @Operation(summary = "待办列表")
    @GetMapping("/todo")
    public R<List<Map<String, Object>>> todo() { return R.ok(workbenchService.getTodoList(SecurityUtils.getCurrentUserId())); }
}
