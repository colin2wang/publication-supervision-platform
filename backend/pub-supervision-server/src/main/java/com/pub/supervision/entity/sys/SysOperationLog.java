package com.pub.supervision.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_operation_log")
public class SysOperationLog {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String module;
    private String description;
    private String requestMethod;
    private String requestUrl;
    private String requestParam;
    private String responseResult;
    private Integer status;
    private String errorMsg;
    private String operIp;
    private String operName;
    private Long operUserId;
    private LocalDateTime operTime;
    private Long costTime;
}
