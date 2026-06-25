package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.common.utils.CaptchaUtils;
import com.pub.supervision.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Tag(name = "认证管理")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ConcurrentHashMap<String, String> captchaStore = new ConcurrentHashMap<>();

    public AuthController(AuthService authService) { this.authService = authService; }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public R<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String captchaKey = params.get("captchaKey");
        String captchaCode = params.get("captcha");
        if (captchaKey != null && captchaCode != null) {
            String stored = captchaStore.remove(captchaKey);
            if (stored == null || !stored.equalsIgnoreCase(captchaCode)) {
                return R.fail(40001, "验证码错误");
            }
        }
        return R.ok(authService.login(params.get("username"), params.get("password")));
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public R<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return R.ok();
    }

    @Operation(summary = "刷新Token")
    @PostMapping("/refresh")
    public R<Map<String, Object>> refresh(@RequestBody Map<String, String> params) {
        return R.ok(authService.refreshToken(params.get("refreshToken")));
    }

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public R<Map<String, Object>> captcha() {
        CaptchaUtils.CaptchaResult result = CaptchaUtils.generate();
        captchaStore.put(result.getUuid(), result.getCode());
        if (captchaStore.size() > 1000) {
            captchaStore.clear();
        }
        return R.ok(Map.of("uuid", result.getUuid(), "image", result.getImage()));
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/userinfo")
    public R<Map<String, Object>> userinfo(@RequestAttribute("userId") Long userId) {
        return R.ok(authService.getUserInfo(userId));
    }
}
