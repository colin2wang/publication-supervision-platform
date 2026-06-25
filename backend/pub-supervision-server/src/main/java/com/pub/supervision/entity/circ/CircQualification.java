package com.pub.supervision.entity.circ;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("circ_qualification")
public class CircQualification {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long merchantId;

    private String qualificationType;

    private String qualificationNo;

    private LocalDateTime issueDate;

    private LocalDateTime expiryDate;

    private String issuingAuthority;

    private String fileUrl;

    private Integer status;

    private Long verifiedBy;

    private LocalDateTime verifiedAt;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
