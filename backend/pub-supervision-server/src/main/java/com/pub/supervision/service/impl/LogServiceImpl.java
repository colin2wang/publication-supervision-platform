package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.sys.SysOperationLog;
import com.pub.supervision.entity.sys.SysLoginLog;
import com.pub.supervision.mapper.SysOperationLogMapper;
import com.pub.supervision.mapper.SysLoginLogMapper;
import com.pub.supervision.service.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements LogService {
    private final SysLoginLogMapper loginLogMapper;
    public LogServiceImpl(SysLoginLogMapper loginLogMapper) { this.loginLogMapper = loginLogMapper; }

    @Override
    public PageResult<SysOperationLog> pageOperationLogs(String module, String operName, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<SysOperationLog> w = new LambdaQueryWrapper<>();
        w.like(module != null && !module.isEmpty(), SysOperationLog::getModule, module)
         .like(operName != null && !operName.isEmpty(), SysOperationLog::getOperName, operName)
         .eq(status != null, SysOperationLog::getStatus, status)
         .orderByDesc(SysOperationLog::getOperTime);
        Page<SysOperationLog> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }

    @Override
    public PageResult<SysLoginLog> pageLoginLogs(String username, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<SysLoginLog> w = new LambdaQueryWrapper<>();
        w.like(username != null && !username.isEmpty(), SysLoginLog::getUsername, username)
         .eq(status != null, SysLoginLog::getStatus, status)
         .orderByDesc(SysLoginLog::getLoginTime);
        Page<SysLoginLog> p = loginLogMapper.selectPage(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
}
