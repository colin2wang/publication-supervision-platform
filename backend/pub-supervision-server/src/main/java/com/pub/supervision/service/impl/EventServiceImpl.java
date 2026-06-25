package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.opi.OpiEvent;
import com.pub.supervision.mapper.OpiEventMapper;
import com.pub.supervision.service.EventService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventServiceImpl extends ServiceImpl<OpiEventMapper, OpiEvent> implements EventService {
    @Override public PageResult<OpiEvent> page(String eventTitle, Integer eventType, int pageNum, int pageSize) {
        LambdaQueryWrapper<OpiEvent> w = new LambdaQueryWrapper<>();
        w.like(eventTitle != null && !eventTitle.isEmpty(), OpiEvent::getEventTitle, eventTitle)
         .eq(eventType != null, OpiEvent::getEventType, eventType).orderByDesc(OpiEvent::getCreatedAt);
        Page<OpiEvent> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public OpiEvent getById(Long id) { OpiEvent e = super.getById(id); if (e == null) throw new BusinessException(ResultCode.NOT_FOUND); return e; }
    @Override public void create(OpiEvent event) { event.setCreatorId(SecurityUtils.getCurrentUserId()); event.setStatus(0); save(event); }
    @Override public void update(OpiEvent event) { updateById(event); }
    @Override public void relateOpinions(Long eventId, List<Long> opinionIds) { OpiEvent e = getById(eventId); e.setRelatedOpinionIds(String.join(",", opinionIds.stream().map(String::valueOf).toList())); updateById(e); }
}
