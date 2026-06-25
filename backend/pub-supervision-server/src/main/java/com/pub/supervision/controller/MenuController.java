package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.sys.SysMenu;
import com.pub.supervision.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "菜单管理")
@RestController
@RequestMapping("/api/v1/system/menus")
public class MenuController {
    private final MenuService menuService;
    public MenuController(MenuService menuService) { this.menuService = menuService; }

    @Operation(summary = "菜单树")
    @GetMapping("/tree")
    public R<List<SysMenu>> tree() { return R.ok(menuService.getTree()); }

    @Operation(summary = "创建菜单")
    @PostMapping
    public R<Void> create(@RequestBody SysMenu menu) { menuService.create(menu); return R.ok(); }

    @Operation(summary = "更新菜单")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysMenu menu) { menu.setId(id); menuService.update(menu); return R.ok(); }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { menuService.delete(id); return R.ok(); }
}
