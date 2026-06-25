package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.circ.CircAlert;
import com.pub.supervision.entity.circ.CircAlertHandle;
import com.pub.supervision.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "预警管理")
@RestController
@RequestMapping("/api/v1/circulation/alerts")
public class AlertController {
    private final AlertService alertService;
    public AlertController(AlertService alertService) { this.alertService = alertService; }

    @Operation(summary = "预警列表")
    @GetMapping
    public R<PageResult<CircAlert>> list(@RequestParam(required = false) Integer alertType, @RequestParam(required = false) Integer alertLevel, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(alertService.page(alertType, alertLevel, status, pageNum, pageSize));
    }
    @Operation(summary = "预警详情")
    @GetMapping("/{id}")
    public R<CircAlert> getById(@PathVariable Long id) { return R.ok(alertService.getById(id)); }
    @Operation(summary = "核实预警")
    @PostMapping("/{id}/verify")
    public R<Void> verify(@PathVariable Long id) { alertService.verify(id); return R.ok(); }
    @Operation(summary = "处理预警")
    @PostMapping("/{id}/handle")
    public R<Void> handle(@PathVariable Long id, @RequestBody CircAlertHandle handle) { alertService.handle(id, handle); return R.ok(); }
    @Operation(summary = "归档预警")
    @PostMapping("/{id}/archive")
    public R<Void> archive(@PathVariable Long id) { alertService.archive(id); return R.ok(); }
    @Operation(summary = "获取处理记录")
    @GetMapping("/{id}/handles")
    public R<List<CircAlertHandle>> handles(@PathVariable Long id) { return R.ok(alertService.getHandles(id)); }
}
