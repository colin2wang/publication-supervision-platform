package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.opi.OpiEvent;
import com.pub.supervision.common.result.PageResult;
import java.util.List;

public interface EventService extends IService<OpiEvent> {
    PageResult<OpiEvent> page(String eventTitle, Integer eventType, int pageNum, int pageSize);
    OpiEvent getById(Long id);
    void create(OpiEvent event);
    void update(OpiEvent event);
    void relateOpinions(Long eventId, List<Long> opinionIds);
}
