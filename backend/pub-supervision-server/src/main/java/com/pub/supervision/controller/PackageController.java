package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.circ.CircPackage;
import com.pub.supervision.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "包裹管理")
@RestController
@RequestMapping("/api/v1/circulation/packages")
public class PackageController {
    private final PackageService pkgService;
    public PackageController(PackageService pkgService) { this.pkgService = pkgService; }

    @Operation(summary = "包裹列表")
    @GetMapping
    public R<PageResult<CircPackage>> list(@RequestParam(required = false) String packageCode, @RequestParam(required = false) Long merchantId, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(pkgService.page(packageCode, merchantId, status, pageNum, pageSize));
    }
    @Operation(summary = "包裹详情")
    @GetMapping("/{id}")
    public R<CircPackage> getById(@PathVariable Long id) { return R.ok(pkgService.getById(id)); }
    @Operation(summary = "创建包裹")
    @PostMapping
    public R<Void> create(@RequestBody CircPackage pkg) { pkgService.create(pkg); return R.ok(); }
    @Operation(summary = "物流追踪")
    @GetMapping("/{id}/tracking")
    public R<List<Map<String, Object>>> tracking(@PathVariable Long id) { return R.ok(pkgService.getTracking(id)); }
    @Operation(summary = "同步数据")
    @PostMapping("/{id}/sync")
    public R<Void> sync(@PathVariable Long id) { pkgService.sync(id); return R.ok(); }
    @Operation(summary = "拦截包裹")
    @PostMapping("/{id}/intercept")
    public R<Void> intercept(@PathVariable Long id) { pkgService.intercept(id); return R.ok(); }
}
