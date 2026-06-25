package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_models")
public class AiModel {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String modelCode;

    private String modelName;

    private Integer modelType;

    private String modelVersion;

    private String description;

    private String endpoint;

    private String apiKey;

    private String config;

    private String metrics;

    private Integer status;

    @Version
    private Integer optVersion;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
