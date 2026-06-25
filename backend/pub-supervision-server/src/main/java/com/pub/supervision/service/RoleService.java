package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.sys.SysRole;
import com.pub.supervision.common.result.PageResult;

public interface RoleService extends IService<SysRole> {
    PageResult<SysRole> page(String roleName, Integer status, int pageNum, int pageSize);
    SysRole getById(Long id);
    void create(SysRole role);
    void update(SysRole role);
    void delete(Long id);
    void assignMenus(Long roleId, java.util.List<Long> menuIds);
}
