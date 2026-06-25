package com.pub.supervision.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("sys_user")
public class SysUser {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String username;
    private String password;
    private String realName;
    private Integer gender;
    private String email;
    private String phone;
    private String avatar;
    private Long departmentId;
    private String position;
    private Integer status;
    private LocalDateTime lastLoginAt;
    private Integer loginCount;
    private String createdBy;
    @Version
    private Integer version;
    @TableLogic
    private Boolean deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    private String updatedBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
