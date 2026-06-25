package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.ai.AiModel;
import java.util.List;
import java.util.Map;

public interface ModelService extends IService<AiModel> {
    List<AiModel> list(String modelType);
    AiModel register(AiModel model);
    Map<String, Object> test(Long id, String input);
    void build(Long id);
}
