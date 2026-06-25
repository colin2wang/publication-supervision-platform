package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.opi.OpiReport;
import com.pub.supervision.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "报告管理")
@RestController
@RequestMapping("/api/v1/opinion/reports")
public class ReportController {
    private final ReportService reportService;
    public ReportController(ReportService reportService) { this.reportService = reportService; }

    @Operation(summary = "报告列表")
    @GetMapping
    public R<PageResult<OpiReport>> list(@RequestParam(required = false) String reportTitle, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(reportService.page(reportTitle, status, pageNum, pageSize));
    }
    @Operation(summary = "报告详情")
    @GetMapping("/{id}")
    public R<OpiReport> getById(@PathVariable Long id) { return R.ok(reportService.getById(id)); }
    @Operation(summary = "生成报告")
    @PostMapping("/generate")
    public R<Void> generate(@RequestBody OpiReport report) { reportService.generate(report); return R.ok(); }
    @Operation(summary = "审核报告")
    @PostMapping("/{id}/review")
    public R<Void> review(@PathVariable Long id, @RequestBody Map<String, Object> params) { reportService.review(id, (Integer) params.get("action"), (String) params.get("remark")); return R.ok(); }
    @Operation(summary = "发布报告")
    @PostMapping("/{id}/publish")
    public R<Void> publish(@PathVariable Long id) { reportService.publish(id); return R.ok(); }
}
