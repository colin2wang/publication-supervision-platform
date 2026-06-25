package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.circ.CircQualification;
import com.pub.supervision.service.QualificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "资质管理")
@RestController
@RequestMapping("/api/v1/circulation")
public class QualificationController {
    private final QualificationService qualService;
    public QualificationController(QualificationService qualService) { this.qualService = qualService; }

    @Operation(summary = "商户资质列表")
    @GetMapping("/merchants/{merchantId}/qualifications")
    public R<List<CircQualification>> listByMerchant(@PathVariable Long merchantId) { return R.ok(qualService.listByMerchant(merchantId)); }

    @Operation(summary = "创建资质")
    @PostMapping("/qualifications")
    public R<Void> create(@RequestBody CircQualification qual) { qualService.create(qual); return R.ok(); }

    @Operation(summary = "审核资质")
    @PostMapping("/qualifications/{id}/verify")
    public R<Void> verify(@PathVariable Long id, @RequestParam Integer result) { qualService.verify(id, result); return R.ok(); }
}
