package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.ai.AiKnowledgeBase;
import com.pub.supervision.entity.ai.AiKnowledgeDocument;
import com.pub.supervision.common.result.PageResult;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface KnowledgeBaseService extends IService<AiKnowledgeBase> {
    PageResult<AiKnowledgeBase> page(String baseName, int pageNum, int pageSize);
    AiKnowledgeBase getById(Long id);
    void create(AiKnowledgeBase kb);
    void delete(Long id);
    List<AiKnowledgeDocument> getDocuments(Long baseId);
    AiKnowledgeDocument uploadDocument(Long baseId, MultipartFile file);
    List<Map<String, Object>> search(Long baseId, String query, int topN);
}
