package com.pub.supervision.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("sys_department")
public class SysDepartment {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long parentId;
    private String deptName;
    private String deptCode;
    private String leader;
    private String phone;
    private String email;
    private Integer sort;
    private Integer status;
    private String ancestors;
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

    @TableField(exist = false)
    private List<SysDepartment> children;
}
