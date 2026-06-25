package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_review_log")
public class IdtReviewLog {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long taskId;
    private Long reviewerId;
    private Integer action;
    private String opinion;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
