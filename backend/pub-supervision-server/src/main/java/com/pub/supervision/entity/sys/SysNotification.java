package com.pub.supervision.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_notification")
public class SysNotification {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String title;
    private String content;
    private Integer type;
    private Long senderId;
    private Long receiverId;
    private Integer isRead;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
