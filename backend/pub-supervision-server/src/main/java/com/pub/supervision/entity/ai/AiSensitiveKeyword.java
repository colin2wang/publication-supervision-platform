package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_sensitive_keyword")
public class AiSensitiveKeyword {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String keyword;

    private Integer category;

    private Integer level;

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
