package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_identification_result")
public class IdtIdentificationResult {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long taskId;
    private Integer resultType;
    private Integer confidence;
    private String detail;
    private String evidence;
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
