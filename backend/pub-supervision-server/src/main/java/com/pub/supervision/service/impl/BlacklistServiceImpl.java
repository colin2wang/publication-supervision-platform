package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.utils.SecurityUtils;
import com.pub.supervision.entity.circ.CircBlacklistWhitelist;
import com.pub.supervision.entity.circ.CircListChangeAudit;
import com.pub.supervision.mapper.CircBlacklistWhitelistMapper;
import com.pub.supervision.mapper.CircListChangeAuditMapper;
import com.pub.supervision.service.BlacklistService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BlacklistServiceImpl extends ServiceImpl<CircBlacklistWhitelistMapper, CircBlacklistWhitelist> implements BlacklistService {
    private final CircListChangeAuditMapper auditMapper;
    public BlacklistServiceImpl(CircListChangeAuditMapper auditMapper) { this.auditMapper = auditMapper; }

    @Override public PageResult<CircBlacklistWhitelist> page(Integer listType, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<CircBlacklistWhitelist> w = new LambdaQueryWrapper<>();
        w.eq(listType != null, CircBlacklistWhitelist::getListType, listType)
         .eq(status != null, CircBlacklistWhitelist::getStatus, status).orderByDesc(CircBlacklistWhitelist::getCreatedAt);
        Page<CircBlacklistWhitelist> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public void add(CircBlacklistWhitelist item) { item.setOperatorId(SecurityUtils.getCurrentUserId()); item.setStatus(0); save(item); }
    @Override public void remove(Long id) { removeById(id); }
    @Override public void appeal(Long id, String reason) { CircBlacklistWhitelist item = getById(id); item.setStatus(3); updateById(item); }
    @Override public void reviewAppeal(Long id, Integer result) { CircBlacklistWhitelist item = getById(id); item.setStatus(result); updateById(item); }
    @Override public List<CircListChangeAudit> getAuditLogs(Long listId) { LambdaQueryWrapper<CircListChangeAudit> w = new LambdaQueryWrapper<>(); w.eq(CircListChangeAudit::getListId, listId); return auditMapper.selectList(w); }
}
