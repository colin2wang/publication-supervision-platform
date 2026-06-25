package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_interception_log")
public class AiInterceptionLog {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String content;

    private String matchKeywords;

    private Integer interceptedCount;

    private String source;

    private Long userId;

    private LocalDateTime createdAt;
}
