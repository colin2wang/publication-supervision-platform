package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_knowledge_document")
public class AiKnowledgeDocument {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long baseId;

    private String docName;

    private Integer docType;

    private String filePath;

    private String content;

    private Integer embeddingStatus;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
