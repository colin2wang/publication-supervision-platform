package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.sys.SysNotification;
import com.pub.supervision.mapper.SysNotificationMapper;
import com.pub.supervision.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends ServiceImpl<SysNotificationMapper, SysNotification> implements NotificationService {
    @Override
    public PageResult<SysNotification> page(Integer type, Integer isRead, int pageNum, int pageSize) {
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<SysNotification> w = new LambdaQueryWrapper<>();
        w.eq(userId != null, SysNotification::getReceiverId, userId)
         .eq(type != null, SysNotification::getType, type)
         .eq(isRead != null, SysNotification::getIsRead, isRead)
         .orderByDesc(SysNotification::getCreatedAt);
        Page<SysNotification> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override
    public void markRead(Long id) { SysNotification n = new SysNotification(); n.setId(id); n.setIsRead(1); updateById(n); }
    @Override
    public void readAll() { Long uid = SecurityUtils.getCurrentUserId(); LambdaQueryWrapper<SysNotification> w = new LambdaQueryWrapper<>(); w.eq(SysNotification::getReceiverId, uid).eq(SysNotification::getIsRead, 0); SysNotification u = new SysNotification(); u.setIsRead(1); update(u, w); }
    @Override
    public int unreadCount() { Long uid = SecurityUtils.getCurrentUserId(); LambdaQueryWrapper<SysNotification> w = new LambdaQueryWrapper<>(); w.eq(SysNotification::getReceiverId, uid).eq(SysNotification::getIsRead, 0); return (int) count(w); }
}
