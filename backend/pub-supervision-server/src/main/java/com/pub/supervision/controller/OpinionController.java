package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.opi.OpiOpinion;
import com.pub.supervision.service.OpinionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "舆情管理")
@RestController
@RequestMapping("/api/v1/opinion/opinions")
public class OpinionController {
    private final OpinionService opinionService;
    public OpinionController(OpinionService opinionService) { this.opinionService = opinionService; }

    @Operation(summary = "舆情列表")
    @GetMapping
    public R<PageResult<OpiOpinion>> list(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer sentiment, @RequestParam(required = false) Integer channelType, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(opinionService.page(keyword, sentiment, channelType, pageNum, pageSize));
    }
    @Operation(summary = "舆情详情")
    @GetMapping("/{id}")
    public R<OpiOpinion> getById(@PathVariable Long id) { return R.ok(opinionService.getById(id)); }
    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { opinionService.delete(id); return R.ok(); }
    @Operation(summary = "批量删除")
    @DeleteMapping("/batch")
    public R<Void> batchDelete(@RequestBody List<Long> ids) { opinionService.batchDelete(ids); return R.ok(); }
    @Operation(summary = "标记")
    @PostMapping("/{id}/mark")
    public R<Void> mark(@PathVariable Long id, @RequestParam Integer isMarked) { opinionService.mark(id, isMarked); return R.ok(); }
}
