package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.sys.SysUser;

public interface UserService extends IService<SysUser> {
    PageResult<SysUser> page(String username, String phone, Integer status, int pageNum, int pageSize);
    SysUser getById(Long id);
    void create(SysUser user);
    void update(SysUser user);
    void delete(Long id);
    void resetPassword(Long id, String newPassword);
    void changeStatus(Long id, Integer status);
}
