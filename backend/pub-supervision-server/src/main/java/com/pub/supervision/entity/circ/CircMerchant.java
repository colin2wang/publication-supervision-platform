package com.pub.supervision.entity.circ;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("circ_merchant")
public class CircMerchant {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String merchantName;

    private String legalPerson;

    private String businessLicense;

    private String contactPhone;

    private String contactEmail;

    private String address;

    private String province;

    private String city;

    private String district;

    private Integer riskLevel;

    private Integer riskScore;

    private Integer status;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
