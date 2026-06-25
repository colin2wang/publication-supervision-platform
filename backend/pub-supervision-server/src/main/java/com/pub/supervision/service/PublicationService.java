package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.idt.IdtPublication;
import com.pub.supervision.common.result.PageResult;
import org.springframework.web.multipart.MultipartFile;

public interface PublicationService extends IService<IdtPublication> {
    PageResult<IdtPublication> page(String title, String isbn, Integer status, int pageNum, int pageSize);
    IdtPublication getById(Long id);
    void create(IdtPublication pub);
    void update(IdtPublication pub);
    void delete(Long id);
    void importExcel(MultipartFile file);
    IdtPublication queryByIsbn(String isbn);
}
