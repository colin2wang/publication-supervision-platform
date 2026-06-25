package com.pub.supervision.controller;

import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.sys.SysRole;
import com.pub.supervision.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/api/v1/system/roles")
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService) { this.roleService = roleService; }

    @Operation(summary = "角色列表")
    @GetMapping
    public R<PageResult<SysRole>> list(@RequestParam(required = false) String roleName, @RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(roleService.page(roleName, status, pageNum, pageSize));
    }

    @Operation(summary = "创建角色")
    @PostMapping
    public R<Void> create(@RequestBody SysRole role) { roleService.create(role); return R.ok(); }

    @Operation(summary = "更新角色")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysRole role) { role.setId(id); roleService.update(role); return R.ok(); }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { roleService.delete(id); return R.ok(); }

    @Operation(summary = "分配菜单")
    @PutMapping("/{id}/menus")
    public R<Void> assignMenus(@PathVariable Long id, @RequestBody List<Long> menuIds) { roleService.assignMenus(id, menuIds); return R.ok(); }
}
