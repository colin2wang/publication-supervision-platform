package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_identification_task")
public class IdtIdentificationTask {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String taskName;
    private Long publicationId;
    private Integer taskType;
    private Integer priority;
    private Integer status;
    private Long assigneeId;
    private Long creatorId;
    private Long reviewerId;
    private LocalDateTime deadline;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String result;
    private String remark;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
