package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.sys.SysUser;
import com.pub.supervision.mapper.SysUserMapper;
import com.pub.supervision.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(PasswordEncoder passwordEncoder) { this.passwordEncoder = passwordEncoder; }

    @Override
    public PageResult<SysUser> page(String username, String phone, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<SysUser> w = new LambdaQueryWrapper<>();
        w.like(username != null && !username.isEmpty(), SysUser::getUsername, username)
         .like(phone != null && !phone.isEmpty(), SysUser::getPhone, phone)
         .eq(status != null, SysUser::getStatus, status)
         .orderByDesc(SysUser::getCreatedAt);
        Page<SysUser> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }

    @Override
    public SysUser getById(Long id) {
        SysUser u = super.getById(id);
        if (u == null) throw new BusinessException(ResultCode.NOT_FOUND);
        return u;
    }

    @Override
    public void create(SysUser user) {
        LambdaQueryWrapper<SysUser> w = new LambdaQueryWrapper<>();
        w.eq(SysUser::getUsername, user.getUsername());
        if (count(w) > 0) throw new BusinessException(ResultCode.PARAM_ERROR, "用户名已存在");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
    }

    @Override
    public void update(SysUser user) { user.setPassword(null); updateById(user); }

    @Override
    public void delete(Long id) { removeById(id); }

    @Override
    public void resetPassword(Long id, String newPassword) {
        SysUser u = new SysUser();
        u.setId(id);
        u.setPassword(passwordEncoder.encode(newPassword));
        updateById(u);
    }

    @Override
    public void changeStatus(Long id, Integer status) {
        SysUser u = new SysUser();
        u.setId(id);
        u.setStatus(status);
        updateById(u);
    }
}
