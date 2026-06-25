package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.circ.CircMerchant;
import com.pub.supervision.service.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商户管理")
@RestController
@RequestMapping("/api/v1/circulation/merchants")
public class MerchantController {
    private final MerchantService merchantService;
    public MerchantController(MerchantService merchantService) { this.merchantService = merchantService; }

    @Operation(summary = "商户列表")
    @GetMapping
    public R<PageResult<CircMerchant>> list(@RequestParam(required = false) String merchantName, @RequestParam(required = false) Integer riskLevel, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(merchantService.page(merchantName, riskLevel, status, pageNum, pageSize));
    }
    @Operation(summary = "商户详情")
    @GetMapping("/{id}")
    public R<CircMerchant> getById(@PathVariable Long id) { return R.ok(merchantService.getById(id)); }
    @Operation(summary = "创建商户")
    @PostMapping
    public R<Void> create(@RequestBody CircMerchant merchant) { merchantService.create(merchant); return R.ok(); }
    @Operation(summary = "更新商户")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody CircMerchant merchant) { merchant.setId(id); merchantService.update(merchant); return R.ok(); }
    @Operation(summary = "删除商户")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { merchantService.delete(id); return R.ok(); }
    @Operation(summary = "计算风险分")
    @PostMapping("/{id}/risk-score")
    public R<Integer> riskScore(@PathVariable Long id) { return R.ok(merchantService.calculateRiskScore(id)); }
}
