package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.sys.SysNotification;
import com.pub.supervision.common.result.PageResult;

public interface NotificationService extends IService<SysNotification> {
    PageResult<SysNotification> page(Integer type, Integer isRead, int pageNum, int pageSize);
    void markRead(Long id);
    void readAll();
    int unreadCount();
}
