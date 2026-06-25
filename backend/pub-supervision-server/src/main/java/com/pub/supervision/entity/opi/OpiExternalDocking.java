package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("opi_external_docking")
public class OpiExternalDocking {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String platformName;

    private Integer platformType;

    private String apiConfig;

    private Integer status;

    private LocalDateTime lastSyncAt;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
