package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.idt.IdtPublication;
import com.pub.supervision.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "出版物管理")
@RestController
@RequestMapping("/api/v1/identification/publications")
public class PublicationController {
    private final PublicationService pubService;
    public PublicationController(PublicationService pubService) { this.pubService = pubService; }

    @Operation(summary = "出版物列表")
    @GetMapping
    public R<PageResult<IdtPublication>> list(@RequestParam(required = false) String title, @RequestParam(required = false) String isbn, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(pubService.page(title, isbn, status, pageNum, pageSize));
    }
    @Operation(summary = "出版物详情")
    @GetMapping("/{id}")
    public R<IdtPublication> getById(@PathVariable Long id) { return R.ok(pubService.getById(id)); }
    @Operation(summary = "创建出版物")
    @PostMapping
    public R<Void> create(@RequestBody IdtPublication pub) { pubService.create(pub); return R.ok(); }
    @Operation(summary = "更新出版物")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody IdtPublication pub) { pub.setId(id); pubService.update(pub); return R.ok(); }
    @Operation(summary = "删除出版物")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { pubService.delete(id); return R.ok(); }
    @Operation(summary = "导入Excel")
    @PostMapping("/import")
    public R<Void> importExcel(@RequestParam("file") MultipartFile file) { pubService.importExcel(file); return R.ok(); }
    @Operation(summary = "按ISBN查询")
    @GetMapping("/isbn/{isbn}")
    public R<IdtPublication> queryByIsbn(@PathVariable String isbn) { return R.ok(pubService.queryByIsbn(isbn)); }
}
