package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.sys.SysUser;
import com.pub.supervision.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/v1/system/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @Operation(summary = "用户列表")
    @GetMapping
    public R<PageResult<SysUser>> list(@RequestParam(required = false) String username, @RequestParam(required = false) String phone, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(userService.page(username, phone, status, pageNum, pageSize));
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public R<SysUser> getById(@PathVariable Long id) { return R.ok(userService.getById(id)); }

    @Operation(summary = "创建用户")
    @PostMapping
    public R<Void> create(@RequestBody SysUser user) { userService.create(user); return R.ok(); }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysUser user) { user.setId(id); userService.update(user); return R.ok(); }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { userService.delete(id); return R.ok(); }

    @Operation(summary = "重置密码")
    @PutMapping("/{id}/password/reset")
    public R<Void> resetPassword(@PathVariable Long id, @RequestBody Map<String, String> params) { userService.resetPassword(id, params.get("password")); return R.ok(); }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public R<Void> changePassword(@RequestBody Map<String, String> params) { return R.ok(); }

    @Operation(summary = "修改状态")
    @PatchMapping("/{id}/status")
    public R<Void> changeStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) { userService.changeStatus(id, params.get("status")); return R.ok(); }
}
