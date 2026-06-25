package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_agent_call")
public class AiAgentCall {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long agentId;

    private Long userId;

    private String input;

    private String output;

    private Integer tokenCount;

    private Long costTime;

    private Integer status;

    private String errorMsg;

    private LocalDateTime createdAt;
}
