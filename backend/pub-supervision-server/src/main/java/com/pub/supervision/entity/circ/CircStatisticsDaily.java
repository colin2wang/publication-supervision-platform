package com.pub.supervision.entity.circ;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("circ_statistics_daily")
public class CircStatisticsDaily {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private LocalDate statDate;

    private Integer totalMerchants;

    private Integer newMerchants;

    private Integer totalPackages;

    private Integer alertCount;

    private Integer handledAlertCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
