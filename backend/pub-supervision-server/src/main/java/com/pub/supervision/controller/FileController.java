package com.pub.supervision.controller;

import com.pub.supervision.common.result.R;
import com.pub.supervision.entity.sys.SysFileUpload;
import com.pub.supervision.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Tag(name = "文件管理")
@RestController
@RequestMapping("/api/v1/system/files")
public class FileController {
    private final FileService fileService;
    public FileController(FileService fileService) { this.fileService = fileService; }

    @Operation(summary = "文件上传")
    @PostMapping("/upload")
    public R<SysFileUpload> upload(@RequestParam("file") MultipartFile file) { return R.ok(fileService.upload(file)); }

    @Operation(summary = "批量上传")
    @PostMapping("/batch-upload")
    public R<List<SysFileUpload>> batchUpload(@RequestParam("files") MultipartFile[] files) { return R.ok(fileService.batchUpload(files)); }

    @Operation(summary = "下载文件")
    @GetMapping("/{id}/download")
    public R<SysFileUpload> download(@PathVariable Long id) { return R.ok(fileService.getById(id)); }
}
