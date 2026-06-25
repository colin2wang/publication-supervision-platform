package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.sys.SysUser;

import java.util.Map;

public interface AuthService extends IService<SysUser> {
    Map<String, Object> login(String username, String password);
    void logout(String token);
    Map<String, Object> refreshToken(String token);
    Map<String, Object> getUserInfo(Long userId);
}
