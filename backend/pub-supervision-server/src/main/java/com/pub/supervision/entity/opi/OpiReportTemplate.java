package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("opi_report_template")
public class OpiReportTemplate {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String templateName;

    private Integer templateType;

    private String content;

    private Integer isDefault;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
