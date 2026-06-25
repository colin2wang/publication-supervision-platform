package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("opi_propagation_edge")
public class OpiPropagationEdge {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long sourceNodeId;

    private Long targetNodeId;

    private Integer edgeType;

    private BigDecimal weight;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
