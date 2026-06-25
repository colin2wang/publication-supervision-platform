package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.idt.IdtIllegalSample;
import com.pub.supervision.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "样本管理")
@RestController
@RequestMapping("/api/v1/identification/samples")
public class SampleController {
    private final SampleService sampleService;
    public SampleController(SampleService sampleService) { this.sampleService = sampleService; }

    @Operation(summary = "样本列表")
    @GetMapping
    public R<PageResult<IdtIllegalSample>> list(@RequestParam(required = false) String sampleName, @RequestParam(required = false) Integer illegalType, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(sampleService.page(sampleName, illegalType, pageNum, pageSize));
    }
    @Operation(summary = "样本详情")
    @GetMapping("/{id}")
    public R<IdtIllegalSample> getById(@PathVariable Long id) { return R.ok(sampleService.getById(id)); }
    @Operation(summary = "创建样本")
    @PostMapping
    public R<Void> create(@RequestBody IdtIllegalSample sample) { sampleService.create(sample); return R.ok(); }
    @Operation(summary = "更新样本")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody IdtIllegalSample sample) { sample.setId(id); sampleService.update(sample); return R.ok(); }
    @Operation(summary = "删除样本")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { sampleService.delete(id); return R.ok(); }
    @Operation(summary = "提取特征")
    @PostMapping("/{id}/extract")
    public R<Map<String, Object>> extract(@PathVariable Long id) { return R.ok(sampleService.extractFeatures(id)); }
    @Operation(summary = "比对")
    @PostMapping("/{id}/compare")
    public R<List<Map<String, Object>>> compare(@PathVariable Long id, @RequestParam(defaultValue = "0.8") Double threshold) { return R.ok(sampleService.compare(id, threshold)); }
    @Operation(summary = "溯源")
    @PostMapping("/{id}/trace")
    public R<Map<String, Object>> trace(@PathVariable Long id) { return R.ok(sampleService.trace(id)); }
}
