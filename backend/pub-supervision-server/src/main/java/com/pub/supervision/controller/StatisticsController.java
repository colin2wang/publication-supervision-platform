package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "统计数据")
@RestController
@RequestMapping("/api/v1/circulation/statistics")
public class StatisticsController {
    private final StatisticsService statsService;
    public StatisticsController(StatisticsService statsService) { this.statsService = statsService; }

    @Operation(summary = "总览")
    @GetMapping("/overview")
    public R<Map<String, Object>> overview(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) { return R.ok(statsService.getOverview(startDate, endDate)); }
    @Operation(summary = "商户风险分布")
    @GetMapping("/merchant-risk")
    public R<Map<String, Object>> merchantRisk() { return R.ok(statsService.getMerchantRisk()); }
    @Operation(summary = "物流趋势")
    @GetMapping("/logistics-trend")
    public R<Map<String, Object>> logisticsTrend(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) { return R.ok(statsService.getLogisticsTrend(startDate, endDate)); }
    @Operation(summary = "预警类型统计")
    @GetMapping("/alert-type")
    public R<Map<String, Object>> alertType(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) { return R.ok(statsService.getAlertType(startDate, endDate)); }
}
