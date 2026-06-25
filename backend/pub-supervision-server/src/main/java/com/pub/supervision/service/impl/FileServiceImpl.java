package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.entity.sys.SysFileUpload;
import com.pub.supervision.mapper.SysFileUploadMapper;
import com.pub.supervision.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl extends ServiceImpl<SysFileUploadMapper, SysFileUpload> implements FileService {
    @Value("${file.upload-path:./uploads}")
    private String uploadPath;

    @Override
    public SysFileUpload upload(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String ext = originalName != null ? originalName.substring(originalName.lastIndexOf(".")) : "";
        String storedName = UUID.randomUUID() + ext;
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();
        try { file.transferTo(new File(dir, storedName)); } catch (IOException e) { throw new RuntimeException("文件上传失败", e); }
        SysFileUpload upload = new SysFileUpload();
        upload.setOriginalName(originalName);
        upload.setStoredName(storedName);
        upload.setFilePath(uploadPath + "/" + storedName);
        upload.setFileSize(file.getSize());
        upload.setFileType(ext);
        upload.setMimeType(file.getContentType());
        save(upload);
        return upload;
    }

    @Override
    public List<SysFileUpload> batchUpload(MultipartFile[] files) {
        List<SysFileUpload> result = new ArrayList<>();
        for (MultipartFile file : files) { result.add(upload(file)); }
        return result;
    }
}
