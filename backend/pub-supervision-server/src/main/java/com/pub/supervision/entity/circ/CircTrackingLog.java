package com.pub.supervision.entity.circ;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("circ_tracking_log")
public class CircTrackingLog {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long packageId;

    private String trackingNo;

    private String location;

    private String status;

    private String description;

    private LocalDateTime eventTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
