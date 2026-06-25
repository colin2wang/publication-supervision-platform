package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("opi_collection_task")
public class OpiCollectionTask {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String taskName;

    private String channelIds;

    private String keywords;

    private String scheduleCron;

    private Integer status;

    private LocalDateTime lastRunAt;

    private LocalDateTime nextRunAt;

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
