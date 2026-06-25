package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("opi_report")
public class OpiReport {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String reportTitle;

    private Integer reportType;

    private String content;

    private Integer generatedBy;

    private Integer status;

    private Long creatorId;

    private Long reviewerId;

    private String reviewRemark;

    private LocalDateTime publishedAt;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
