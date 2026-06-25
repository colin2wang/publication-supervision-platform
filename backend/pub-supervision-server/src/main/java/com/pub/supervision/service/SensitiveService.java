package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.ai.AiInterceptionLog;
import com.pub.supervision.common.result.PageResult;
import java.util.Map;

public interface SensitiveService extends IService<AiInterceptionLog> {
    Map<String, Object> textCheck(String text);
    Map<String, Object> imageCheck(String imageUrl);
    PageResult<AiInterceptionLog> getLogs(String source, int pageNum, int pageSize);
}
