package com.pub.supervision.entity.circ;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("circ_list_change_audit")
public class CircListChangeAudit {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long listId;

    private Integer changeType;

    private String beforeData;

    private String afterData;

    private Long auditorId;

    private Integer auditResult;

    private String auditRemark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
