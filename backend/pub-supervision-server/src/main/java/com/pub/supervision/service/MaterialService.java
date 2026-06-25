package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.idt.IdtIdentificationMaterial;
import java.util.List;

public interface MaterialService extends IService<IdtIdentificationMaterial> {
    List<IdtIdentificationMaterial> listByTaskId(Long taskId);
    IdtIdentificationMaterial upload(Long taskId, org.springframework.web.multipart.MultipartFile file);
    void delete(Long id);
    void triggerOcr(Long id);
}
