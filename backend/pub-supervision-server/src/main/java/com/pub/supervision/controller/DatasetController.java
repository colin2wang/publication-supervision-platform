package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.idt.IdtDataset;
import com.pub.supervision.service.DatasetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Tag(name = "数据集管理")
@RestController
@RequestMapping("/api/v1/identification/datasets")
public class DatasetController {
    private final DatasetService datasetService;
    public DatasetController(DatasetService datasetService) { this.datasetService = datasetService; }

    @Operation(summary = "数据集列表")
    @GetMapping
    public R<PageResult<IdtDataset>> list(@RequestParam(required = false) String datasetName, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(datasetService.page(datasetName, status, pageNum, pageSize));
    }
    @Operation(summary = "数据集详情")
    @GetMapping("/{id}")
    public R<IdtDataset> getById(@PathVariable Long id) { return R.ok(datasetService.getById(id)); }
    @Operation(summary = "创建数据集")
    @PostMapping
    public R<Void> create(@RequestBody IdtDataset ds) { datasetService.create(ds); return R.ok(); }
    @Operation(summary = "更新数据集")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody IdtDataset ds) { ds.setId(id); datasetService.update(ds); return R.ok(); }
    @Operation(summary = "删除数据集")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { datasetService.delete(id); return R.ok(); }
    @Operation(summary = "上传数据项")
    @PostMapping("/{id}/items")
    public R<Void> uploadItem(@PathVariable Long id, @RequestParam("file") MultipartFile file) { datasetService.uploadItem(id, file); return R.ok(); }
    @Operation(summary = "标注数据项")
    @PostMapping("/{id}/annotate")
    public R<Void> annotate(@PathVariable Long id, @RequestBody Map<String, Object> params) { datasetService.annotate(id, (Long) params.get("itemId"), (String) params.get("label")); return R.ok(); }
    @Operation(summary = "审核数据集")
    @PostMapping("/{id}/review")
    public R<Void> review(@PathVariable Long id) { datasetService.review(id); return R.ok(); }
    @Operation(summary = "发布数据集")
    @PostMapping("/{id}/publish")
    public R<Void> publish(@PathVariable Long id) { datasetService.publish(id); return R.ok(); }
}
