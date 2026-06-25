package com.pub.supervision.entity.circ;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("circ_alert")
public class CircAlert {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Integer alertType;

    private Integer alertLevel;

    private Long merchantId;

    private Long packageId;

    private String title;

    private String description;

    private String source;

    private Integer status;

    private Long handlerId;

    private String handleResult;

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
