package com.pub.supervision.entity.circ;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("circ_alert_handle")
public class CircAlertHandle {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long alertId;

    private Long handlerId;

    private Integer handleAction;

    private String handleResult;

    private String handleRemark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
