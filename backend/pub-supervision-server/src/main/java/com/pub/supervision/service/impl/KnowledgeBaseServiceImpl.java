package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.ai.AiKnowledgeBase;
import com.pub.supervision.entity.ai.AiKnowledgeDocument;
import com.pub.supervision.entity.sys.SysFileUpload;
import com.pub.supervision.mapper.AiKnowledgeBaseMapper;
import com.pub.supervision.mapper.AiKnowledgeDocumentMapper;
import com.pub.supervision.service.FileService;
import com.pub.supervision.service.KnowledgeBaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Service
public class KnowledgeBaseServiceImpl extends ServiceImpl<AiKnowledgeBaseMapper, AiKnowledgeBase> implements KnowledgeBaseService {
    private final AiKnowledgeDocumentMapper docMapper;
    private final FileService fileService;
    public KnowledgeBaseServiceImpl(AiKnowledgeDocumentMapper docMapper, FileService fileService) { this.docMapper = docMapper; this.fileService = fileService; }

    @Override public PageResult<AiKnowledgeBase> page(String baseName, int pageNum, int pageSize) {
        LambdaQueryWrapper<AiKnowledgeBase> w = new LambdaQueryWrapper<>();
        w.like(baseName != null && !baseName.isEmpty(), AiKnowledgeBase::getBaseName, baseName).orderByDesc(AiKnowledgeBase::getCreatedAt);
        Page<AiKnowledgeBase> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public AiKnowledgeBase getById(Long id) { AiKnowledgeBase kb = super.getById(id); if (kb == null) throw new BusinessException(ResultCode.NOT_FOUND); return kb; }
    @Override public void create(AiKnowledgeBase kb) { kb.setDocCount(0); save(kb); }
    @Override public void delete(Long id) { removeById(id); }
    @Override public List<AiKnowledgeDocument> getDocuments(Long baseId) { LambdaQueryWrapper<AiKnowledgeDocument> w = new LambdaQueryWrapper<>(); w.eq(AiKnowledgeDocument::getBaseId, baseId); return docMapper.selectList(w); }
    @Override public AiKnowledgeDocument uploadDocument(Long baseId, MultipartFile file) {
        SysFileUpload upload = fileService.upload(file);
        AiKnowledgeDocument doc = new AiKnowledgeDocument(); doc.setBaseId(baseId); doc.setDocName(file.getOriginalFilename()); doc.setFilePath(upload.getFilePath()); doc.setEmbeddingStatus(0); docMapper.insert(doc);
        AiKnowledgeBase kb = getById(baseId); kb.setDocCount(kb.getDocCount() + 1); updateById(kb); return doc;
    }
    @Override public List<Map<String, Object>> search(Long baseId, String query, int topN) {
        List<Map<String, Object>> results = new ArrayList<>();
        LambdaQueryWrapper<AiKnowledgeDocument> w = new LambdaQueryWrapper<>(); w.eq(AiKnowledgeDocument::getBaseId, baseId);
        for (AiKnowledgeDocument doc : docMapper.selectList(w)) { Map<String, Object> item = new HashMap<>(); item.put("docId", doc.getId()); item.put("docName", doc.getDocName()); item.put("score", 0.85); results.add(item); if (results.size() >= topN) break; }
        return results;
    }
}
