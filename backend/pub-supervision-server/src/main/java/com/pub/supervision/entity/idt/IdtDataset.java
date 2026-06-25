package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_dataset")
public class IdtDataset {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String datasetName;
    private String description;
    private Integer datasetType;
    private Integer totalItems;
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
