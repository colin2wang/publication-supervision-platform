package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_traceability_result")
public class IdtTraceabilityResult {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long taskId;
    private Long sampleId;
    private Integer matchScore;
    private String traceChain;
    private String source;
    private Long operatorId;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
