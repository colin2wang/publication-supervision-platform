package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_model")
public class AiModel {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String modelName;

    private Integer modelType;

    private String modelVersion;

    private String provider;

    private String endpoint;

    private String apiKey;

    private String config;

    private Integer status;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
