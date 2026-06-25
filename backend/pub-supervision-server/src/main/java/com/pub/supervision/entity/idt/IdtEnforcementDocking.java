package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_enforcement_docking")
public class IdtEnforcementDocking {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long taskId;
    private Long workOrderId;
    private String department;
    private String contactPerson;
    private String contactPhone;
    private Integer status;
    private String result;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
