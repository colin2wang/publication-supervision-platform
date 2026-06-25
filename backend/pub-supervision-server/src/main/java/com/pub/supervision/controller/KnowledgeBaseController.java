package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.ai.AiKnowledgeBase;
import com.pub.supervision.entity.ai.AiKnowledgeDocument;
import com.pub.supervision.service.KnowledgeBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Tag(name = "知识库管理")
@RestController
@RequestMapping("/api/v1/ai/knowledge-bases")
public class KnowledgeBaseController {
    private final KnowledgeBaseService kbService;
    public KnowledgeBaseController(KnowledgeBaseService kbService) { this.kbService = kbService; }

    @Operation(summary = "知识库列表")
    @GetMapping
    public R<PageResult<AiKnowledgeBase>> list(@RequestParam(required = false) String baseName, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(kbService.page(baseName, pageNum, pageSize));
    }
    @Operation(summary = "知识库详情")
    @GetMapping("/{id}")
    public R<AiKnowledgeBase> getById(@PathVariable Long id) { return R.ok(kbService.getById(id)); }
    @Operation(summary = "创建知识库")
    @PostMapping
    public R<Void> create(@RequestBody AiKnowledgeBase kb) { kbService.create(kb); return R.ok(); }
    @Operation(summary = "删除知识库")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { kbService.delete(id); return R.ok(); }
    @Operation(summary = "文档列表")
    @GetMapping("/{id}/documents")
    public R<List<AiKnowledgeDocument>> documents(@PathVariable Long id) { return R.ok(kbService.getDocuments(id)); }
    @Operation(summary = "上传文档")
    @PostMapping("/{id}/documents")
    public R<AiKnowledgeDocument> uploadDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) { return R.ok(kbService.uploadDocument(id, file)); }
    @Operation(summary = "搜索")
    @GetMapping("/{id}/search")
    public R<List<Map<String, Object>>> search(@PathVariable Long id, @RequestParam String query, @RequestParam(defaultValue = "10") int topN) { return R.ok(kbService.search(id, query, topN)); }
}
