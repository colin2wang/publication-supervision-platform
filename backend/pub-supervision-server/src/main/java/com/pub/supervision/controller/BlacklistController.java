package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.circ.CircBlacklistWhitelist;
import com.pub.supervision.service.BlacklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "黑白名单管理")
@RestController
@RequestMapping("/api/v1/circulation/lists")
public class BlacklistController {
    private final BlacklistService blacklistService;
    public BlacklistController(BlacklistService blacklistService) { this.blacklistService = blacklistService; }

    @Operation(summary = "名单列表")
    @GetMapping
    public R<PageResult<CircBlacklistWhitelist>> list(@RequestParam(required = false) Integer listType, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(blacklistService.page(listType, status, pageNum, pageSize));
    }
    @Operation(summary = "添加")
    @PostMapping
    public R<Void> add(@RequestBody CircBlacklistWhitelist item) { blacklistService.add(item); return R.ok(); }
    @Operation(summary = "移除")
    @DeleteMapping("/{id}")
    public R<Void> remove(@PathVariable Long id) { blacklistService.remove(id); return R.ok(); }
    @Operation(summary = "申诉")
    @PostMapping("/{id}/appeal")
    public R<Void> appeal(@PathVariable Long id, @RequestBody Map<String, String> params) { blacklistService.appeal(id, params.get("reason")); return R.ok(); }
}
