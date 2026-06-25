package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.sys.SysOperationLog;
import com.pub.supervision.entity.sys.SysLoginLog;
import com.pub.supervision.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "日志管理")
@RestController
@RequestMapping("/api/v1/system/logs")
public class LogController {
    private final LogService logService;
    public LogController(LogService logService) { this.logService = logService; }

    @Operation(summary = "操作日志")
    @GetMapping("/operation")
    public R<PageResult<SysOperationLog>> operationLogs(@RequestParam(required = false) String module, @RequestParam(required = false) String operName, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(logService.pageOperationLogs(module, operName, status, pageNum, pageSize));
    }

    @Operation(summary = "登录日志")
    @GetMapping("/login")
    public R<PageResult<SysLoginLog>> loginLogs(@RequestParam(required = false) String username, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(logService.pageLoginLogs(username, status, pageNum, pageSize));
    }
}
