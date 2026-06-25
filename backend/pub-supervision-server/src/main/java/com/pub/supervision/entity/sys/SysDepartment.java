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
    private String deptName;
    private Long parentId;
    private Integer orderNum;
    private String leader;
    private String phone;
    private String email;
    private Integer status;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private List<SysDepartment> children;
}
