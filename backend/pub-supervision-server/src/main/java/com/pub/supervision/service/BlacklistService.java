package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.circ.CircBlacklistWhitelist;
import com.pub.supervision.entity.circ.CircListChangeAudit;
import com.pub.supervision.common.result.PageResult;
import java.util.List;

public interface BlacklistService extends IService<CircBlacklistWhitelist> {
    PageResult<CircBlacklistWhitelist> page(Integer listType, Integer status, int pageNum, int pageSize);
    void add(CircBlacklistWhitelist item);
    void remove(Long id);
    void appeal(Long id, String reason);
    void reviewAppeal(Long id, Integer result);
    List<CircListChangeAudit> getAuditLogs(Long listId);
}
