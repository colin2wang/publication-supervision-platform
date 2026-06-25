package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_agent")
public class AiAgent {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String agentName;

    private Integer agentType;

    private String description;

    private Long modelId;

    private String systemPrompt;

    private String config;

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
