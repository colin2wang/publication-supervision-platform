package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_dataset_item")
public class IdtDatasetItem {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long datasetId;
    private String itemName;
    private String itemContent;
    private String itemLabel;
    private String features;
    private Integer status;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
