package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_illegal_sample")
public class IdtIllegalSample {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String sampleName;
    private String isbn;
    private String author;
    private String publisher;
    private Integer illegalType;
    private String features;
    private String coverUrl;
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
