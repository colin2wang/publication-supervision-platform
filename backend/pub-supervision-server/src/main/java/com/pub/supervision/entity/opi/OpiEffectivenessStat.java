package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("opi_effectiveness_stat")
public class OpiEffectivenessStat {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long departmentId;

    private String departmentName;

    private String period;

    private Integer totalOpinions;

    private Integer handledOpinions;

    private BigDecimal avgResponseTime;

    private BigDecimal satisfactionScore;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
