package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.sys.SysFileUpload;
import org.springframework.web.multipart.MultipartFile;

public interface FileService extends IService<SysFileUpload> {
    SysFileUpload upload(MultipartFile file);
    java.util.List<SysFileUpload> batchUpload(MultipartFile[] files);
}
