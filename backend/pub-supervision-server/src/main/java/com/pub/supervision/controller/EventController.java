package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.opi.OpiEvent;
import com.pub.supervision.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "事件管理")
@RestController
@RequestMapping("/api/v1/opinion/events")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService) { this.eventService = eventService; }

    @Operation(summary = "事件列表")
    @GetMapping
    public R<PageResult<OpiEvent>> list(@RequestParam(required = false) String eventTitle, @RequestParam(required = false) Integer eventType, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(eventService.page(eventTitle, eventType, pageNum, pageSize));
    }
    @Operation(summary = "事件详情")
    @GetMapping("/{id}")
    public R<OpiEvent> getById(@PathVariable Long id) { return R.ok(eventService.getById(id)); }
    @Operation(summary = "创建事件")
    @PostMapping
    public R<Void> create(@RequestBody OpiEvent event) { eventService.create(event); return R.ok(); }
    @Operation(summary = "更新事件")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody OpiEvent event) { event.setId(id); eventService.update(event); return R.ok(); }
    @Operation(summary = "关联舆情")
    @PostMapping("/{id}/relate-opinions")
    public R<Void> relateOpinions(@PathVariable Long id, @RequestBody List<Long> opinionIds) { eventService.relateOpinions(id, opinionIds); return R.ok(); }
}
