package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_quality_check_log")
public class AiQualityCheckLog {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long ruleId;

    private Integer targetType;

    private Long targetId;

    private Integer checkResult;

    private String detail;

    private Long checkerId;

    private LocalDateTime createdAt;
}
