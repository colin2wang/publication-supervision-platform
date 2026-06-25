package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_digital_human")
public class AiDigitalHuman {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String humanName;

    private String avatar;

    private String voiceId;

    private String description;

    private Long modelId;

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
