package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.sys.SysNotification;
import com.pub.supervision.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "通知管理")
@RestController
@RequestMapping("/api/v1/system/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) { this.notificationService = notificationService; }

    @Operation(summary = "通知列表")
    @GetMapping
    public R<PageResult<SysNotification>> list(@RequestParam(required = false) Integer type, @RequestParam(required = false) Integer isRead, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(notificationService.page(type, isRead, pageNum, pageSize));
    }

    @Operation(summary = "标记已读")
    @PatchMapping("/{id}/read")
    public R<Void> markRead(@PathVariable Long id) { notificationService.markRead(id); return R.ok(); }

    @Operation(summary = "全部已读")
    @PostMapping("/read-all")
    public R<Void> readAll() { notificationService.readAll(); return R.ok(); }

    @Operation(summary = "未读数量")
    @GetMapping("/unread-count")
    public R<Integer> unreadCount() { return R.ok(notificationService.unreadCount()); }
}
