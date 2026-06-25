package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_translation_task")
public class AiTranslationTask {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String taskName;

    private String sourceLang;

    private String targetLang;

    private Integer sourceType;

    private String sourceContent;

    private String filePath;

    private String resultContent;

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
