package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("opi_propagation_node")
public class OpiPropagationNode {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long opinionId;

    private String platform;

    private String nodeId;

    private Integer nodeType;

    private String content;

    private Integer influence;

    private Integer 转发Count;

    private Integer commentCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
