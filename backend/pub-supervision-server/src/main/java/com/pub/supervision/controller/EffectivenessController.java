package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.service.EffectivenessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "效能评估")
@RestController
@RequestMapping("/api/v1/opinion/effectiveness")
public class EffectivenessController {
    private final EffectivenessService effectivenessService;
    public EffectivenessController(EffectivenessService effectivenessService) { this.effectivenessService = effectivenessService; }

    @Operation(summary = "效能总览")
    @GetMapping("/overview")
    public R<Map<String, Object>> overview(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) { return R.ok(effectivenessService.getOverview(startDate, endDate)); }
    @Operation(summary = "排名")
    @GetMapping("/ranking")
    public R<Map<String, Object>> ranking(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) { return R.ok(effectivenessService.getRanking(startDate, endDate)); }
    @Operation(summary = "部门对比")
    @GetMapping("/department-compare")
    public R<Map<String, Object>> departmentCompare(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) { return R.ok(effectivenessService.getDepartmentCompare(startDate, endDate)); }
}
