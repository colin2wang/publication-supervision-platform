package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_identification_material")
public class IdtIdentificationMaterial {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long taskId;
    private String materialName;
    private String materialType;
    private String filePath;
    private Long fileSize;
    private Integer ocrStatus;
    private String ocrResult;
    private Long uploaderId;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
