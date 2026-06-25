package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.entity.idt.IdtIdentificationMaterial;
import com.pub.supervision.entity.sys.SysFileUpload;
import com.pub.supervision.mapper.IdtIdentificationMaterialMapper;
import com.pub.supervision.service.MaterialService;
import com.pub.supervision.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class MaterialServiceImpl extends ServiceImpl<IdtIdentificationMaterialMapper, IdtIdentificationMaterial> implements MaterialService {
    private final FileService fileService;
    public MaterialServiceImpl(FileService fileService) { this.fileService = fileService; }

    @Override public List<IdtIdentificationMaterial> listByTaskId(Long taskId) { LambdaQueryWrapper<IdtIdentificationMaterial> w = new LambdaQueryWrapper<>(); w.eq(IdtIdentificationMaterial::getTaskId, taskId); return list(w); }
    @Override public IdtIdentificationMaterial upload(Long taskId, MultipartFile file) {
        SysFileUpload upload = fileService.upload(file);
        IdtIdentificationMaterial m = new IdtIdentificationMaterial(); m.setTaskId(taskId); m.setMaterialName(file.getOriginalFilename()); m.setMaterialType(file.getContentType()); m.setFilePath(upload.getFilePath()); m.setFileSize(file.getSize()); m.setOcrStatus(0); save(m); return m;
    }
    @Override public void delete(Long id) { removeById(id); }
    @Override public void triggerOcr(Long id) { IdtIdentificationMaterial m = getById(id); m.setOcrStatus(1); updateById(m); }
}
