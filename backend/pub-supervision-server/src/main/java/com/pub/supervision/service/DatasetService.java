package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.idt.IdtDataset;
import com.pub.supervision.common.result.PageResult;
import org.springframework.web.multipart.MultipartFile;

public interface DatasetService extends IService<IdtDataset> {
    PageResult<IdtDataset> page(String datasetName, Integer status, int pageNum, int pageSize);
    IdtDataset getById(Long id);
    void create(IdtDataset dataset);
    void update(IdtDataset dataset);
    void delete(Long id);
    void uploadItem(Long datasetId, MultipartFile file);
    void annotate(Long datasetId, Long itemId, String label);
    void review(Long datasetId);
    void publish(Long datasetId);
}
