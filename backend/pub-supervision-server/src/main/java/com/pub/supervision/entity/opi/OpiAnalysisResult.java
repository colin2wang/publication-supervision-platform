package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("opi_analysis_result")
public class OpiAnalysisResult {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long opinionId;

    private Long taskId;

    private Integer analysisType;

    private String result;

    private BigDecimal confidence;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
