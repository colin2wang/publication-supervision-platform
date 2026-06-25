package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_datasets")
public class IdtDataset {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String datasetName;
    private Integer datasetType;
    private String datasetVersion;
    private String description;
    private Integer sampleCount;
    private Double qualityScore;
    private Integer status;
    private String storagePath;
    private Long creatorId;
    @Version
    private Integer optVersion;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
