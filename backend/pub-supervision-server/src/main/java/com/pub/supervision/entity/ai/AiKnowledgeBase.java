package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_knowledge_base")
public class AiKnowledgeBase {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String baseName;

    private String description;

    private Integer baseType;

    private Integer docCount;

    private Integer status;

    private Long creatorId;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
