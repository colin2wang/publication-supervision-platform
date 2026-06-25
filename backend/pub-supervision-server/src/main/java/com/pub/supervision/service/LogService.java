package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.sys.SysOperationLog;
import com.pub.supervision.common.result.PageResult;

public interface LogService extends IService<SysOperationLog> {
    PageResult<SysOperationLog> pageOperationLogs(String module, String operName, Integer status, int pageNum, int pageSize);
    PageResult<com.pub.supervision.entity.sys.SysLoginLog> pageLoginLogs(String username, Integer status, int pageNum, int pageSize);
}
