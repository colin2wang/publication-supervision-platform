package com.pub.supervision.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("sys_menu")
public class SysMenu {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long parentId;
    private String menuName;
    private Integer menuType;
    private String permissionCode;
    private String path;
    private String component;
    private String icon;
    private Integer sort;
    private Boolean visible;
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

    @TableField(exist = false)
    private List<SysMenu> children;
}
