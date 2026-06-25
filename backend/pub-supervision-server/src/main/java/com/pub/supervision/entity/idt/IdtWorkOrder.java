package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_work_order")
public class IdtWorkOrder {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String orderTitle;
    private Integer orderType;
    private Long taskId;
    private Long publisherId;
    private Integer status;
    private Long handlerId;
    private String handlerResult;
    private LocalDateTime handledAt;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
