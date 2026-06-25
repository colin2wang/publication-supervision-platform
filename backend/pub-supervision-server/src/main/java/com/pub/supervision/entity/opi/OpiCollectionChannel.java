package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("opi_collection_channel")
public class OpiCollectionChannel {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String channelName;

    private Integer channelType;

    private String url;

    private String config;

    private Integer status;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
