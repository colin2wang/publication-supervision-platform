package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("opi_event")
public class OpiEvent {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String eventTitle;

    private Integer eventType;

    private String description;

    private Integer level;

    private String relatedOpinionIds;

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
