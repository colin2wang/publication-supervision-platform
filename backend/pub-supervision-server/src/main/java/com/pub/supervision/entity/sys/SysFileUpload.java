package com.pub.supervision.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_file_upload")
public class SysFileUpload {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String originalName;
    private String storedName;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private String mimeType;
    private Long uploaderId;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
