package com.pub.supervision.entity.opi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("opi_opinion")
public class OpiOpinion {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String title;

    private String content;

    private String source;

    private String sourceUrl;

    private Integer channelType;

    private String author;

    private Integer sentiment;

    private BigDecimal sentimentScore;

    private Integer heat;

    private String province;

    private String city;

    private Integer isMarked;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
