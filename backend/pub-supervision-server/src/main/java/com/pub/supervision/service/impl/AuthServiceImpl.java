package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.utils.JwtUtils;
import com.pub.supervision.entity.sys.SysUser;
import com.pub.supervision.mapper.SysUserMapper;
import com.pub.supervision.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements AuthService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        SysUser user = getOne(wrapper);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException(ResultCode.FORBIDDEN, "账号已被禁用");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getUsername());
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", token);
        result.put("refreshToken", token);
        result.put("expiresIn", 7200);
        return result;
    }

    @Override
    public void logout(String token) { }

    @Override
    public Map<String, Object> refreshToken(String token) {
        String newToken = jwtUtils.refreshToken(token);
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", newToken);
        result.put("expiresIn", 7200);
        return result;
    }

    @Override
    public Map<String, Object> getUserInfo(Long userId) {
        SysUser user = getById(userId);
        if (user == null) throw new BusinessException(ResultCode.NOT_FOUND);
        Map<String, Object> info = new HashMap<>();
        info.put("id", user.getId());
        info.put("username", user.getUsername());
        info.put("realName", user.getRealName());
        info.put("avatar", user.getAvatar());
        info.put("email", user.getEmail());
        info.put("phone", user.getPhone());
        return info;
    }
}
