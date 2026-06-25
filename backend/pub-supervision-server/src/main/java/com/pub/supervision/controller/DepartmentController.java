package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.sys.SysDepartment;
import com.pub.supervision.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "部门管理")
@RestController
@RequestMapping("/api/v1/system/departments")
public class DepartmentController {
    private final DepartmentService deptService;
    public DepartmentController(DepartmentService deptService) { this.deptService = deptService; }

    @Operation(summary = "部门树")
    @GetMapping("/tree")
    public R<List<SysDepartment>> tree() { return R.ok(deptService.getTree()); }

    @Operation(summary = "创建部门")
    @PostMapping
    public R<Void> create(@RequestBody SysDepartment dept) { deptService.create(dept); return R.ok(); }

    @Operation(summary = "更新部门")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysDepartment dept) { dept.setId(id); deptService.update(dept); return R.ok(); }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) { deptService.delete(id); return R.ok(); }
}
