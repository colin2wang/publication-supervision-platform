package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.idt.IdtPublication;
import com.pub.supervision.mapper.IdtPublicationMapper;
import com.pub.supervision.service.PublicationService;
import com.alibaba.excel.EasyExcel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class PublicationServiceImpl extends ServiceImpl<IdtPublicationMapper, IdtPublication> implements PublicationService {
    @Override
    public PageResult<IdtPublication> page(String title, String isbn, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<IdtPublication> w = new LambdaQueryWrapper<>();
        w.like(title != null && !title.isEmpty(), IdtPublication::getTitle, title)
         .like(isbn != null && !isbn.isEmpty(), IdtPublication::getIsbn, isbn)
         .eq(status != null, IdtPublication::getStatus, status)
         .orderByDesc(IdtPublication::getCreatedAt);
        Page<IdtPublication> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public IdtPublication getById(Long id) { IdtPublication pub = super.getById(id); if (pub == null) throw new BusinessException(ResultCode.NOT_FOUND); return pub; }
    @Override public void create(IdtPublication pub) { save(pub); }
    @Override public void update(IdtPublication pub) { updateById(pub); }
    @Override public void delete(Long id) { removeById(id); }
    @Override public void importExcel(MultipartFile file) { try { List<IdtPublication> list = EasyExcel.read(file.getInputStream()).head(IdtPublication.class).sheet().doReadSync(); saveBatch(list); } catch (IOException e) { throw new BusinessException("导入失败: " + e.getMessage()); } }
    @Override public IdtPublication queryByIsbn(String isbn) { LambdaQueryWrapper<IdtPublication> w = new LambdaQueryWrapper<>(); w.eq(IdtPublication::getIsbn, isbn); return getOne(w); }
}
