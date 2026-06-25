package com.pub.supervision.entity.ai;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_conversation")
public class AiConversation {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long digitalHumanId;

    private Long userId;

    private String sessionId;

    private String role;

    private String content;

    private LocalDateTime createdAt;
}
