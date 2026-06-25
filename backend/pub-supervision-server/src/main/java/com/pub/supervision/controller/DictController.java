package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.sys.SysDictData;
import com.pub.supervision.entity.sys.SysDictType;
import com.pub.supervision.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "字典管理")
@RestController
@RequestMapping("/api/v1/system/dict")
public class DictController {
    private final DictService dictService;
    public DictController(DictService dictService) { this.dictService = dictService; }

    @Operation(summary = "字典类型列表")
    @GetMapping("/types")
    public R<List<SysDictType>> types() { return R.ok(dictService.getTypes()); }

    @Operation(summary = "按编码获取字典数据")
    @GetMapping("/data/{dictCode}")
    public R<List<SysDictData>> data(@PathVariable String dictCode) { return R.ok(dictService.getDataByCode(dictCode)); }
}
