package com.pub.supervision.entity.circ;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("circ_blacklist_whitelist")
public class CircBlacklistWhitelist {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Integer listType;

    private Integer targetType;

    private Long targetId;

    private String reason;

    private Long operatorId;

    private Integer status;

    private LocalDateTime expireDate;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
