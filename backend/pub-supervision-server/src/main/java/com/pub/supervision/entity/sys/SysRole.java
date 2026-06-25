package com.pub.supervision.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_role")
public class SysRole {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String roleCode;
    private String roleName;
    private Integer dataScope;
    private Integer sort;
    private Integer status;
    private Long createdBy;
    @Version
    private Integer version;
    @TableLogic
    private Boolean deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    private Long updatedBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
