package com.pub.supervision.entity.idt;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("idt_publication")
public class IdtPublication {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String title;
    private String isbn;
    private String author;
    private String publisher;
    private Integer publishYear;
    private String category;
    private String coverUrl;
    private String description;
    private Integer status;
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
