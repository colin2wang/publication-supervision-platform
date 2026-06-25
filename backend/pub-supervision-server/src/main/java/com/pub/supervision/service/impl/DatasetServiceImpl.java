package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.idt.IdtDataset;
import com.pub.supervision.entity.idt.IdtDatasetItem;
import com.pub.supervision.mapper.IdtDatasetMapper;
import com.pub.supervision.mapper.IdtDatasetItemMapper;
import com.pub.supervision.service.DatasetService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class DatasetServiceImpl extends ServiceImpl<IdtDatasetMapper, IdtDataset> implements DatasetService {
    private final IdtDatasetItemMapper itemMapper;
    public DatasetServiceImpl(IdtDatasetItemMapper itemMapper) { this.itemMapper = itemMapper; }

    @Override public PageResult<IdtDataset> page(String datasetName, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<IdtDataset> w = new LambdaQueryWrapper<>();
        w.like(datasetName != null && !datasetName.isEmpty(), IdtDataset::getDatasetName, datasetName)
         .eq(status != null, IdtDataset::getStatus, status).orderByDesc(IdtDataset::getCreatedAt);
        Page<IdtDataset> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public IdtDataset getById(Long id) { IdtDataset d = super.getById(id); if (d == null) throw new BusinessException(ResultCode.NOT_FOUND); return d; }
    @Override public void create(IdtDataset ds) { ds.setTotalItems(0); save(ds); }
    @Override public void update(IdtDataset ds) { updateById(ds); }
    @Override public void delete(Long id) { removeById(id); }
    @Override public void uploadItem(Long datasetId, MultipartFile file) {
        try { String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        IdtDatasetItem item = new IdtDatasetItem(); item.setDatasetId(datasetId); item.setItemName(file.getOriginalFilename()); item.setItemContent(content); item.setStatus(0); itemMapper.insert(item);
        IdtDataset ds = getById(datasetId); ds.setTotalItems(ds.getTotalItems() + 1); updateById(ds); } catch (IOException e) { throw new BusinessException("文件读取失败"); }
    }
    @Override public void annotate(Long datasetId, Long itemId, String label) { IdtDatasetItem item = itemMapper.selectById(itemId); item.setItemLabel(label); item.setStatus(1); itemMapper.updateById(item); }
    @Override public void review(Long datasetId) { IdtDataset ds = getById(datasetId); ds.setStatus(2); updateById(ds); }
    @Override public void publish(Long datasetId) { IdtDataset ds = getById(datasetId); ds.setStatus(3); updateById(ds); }
}
