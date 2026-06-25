package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.entity.ai.AiModel;
import com.pub.supervision.mapper.AiModelMapper;
import com.pub.supervision.service.ModelService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ModelServiceImpl extends ServiceImpl<AiModelMapper, AiModel> implements ModelService {
    @Override public List<AiModel> list(String modelType) { LambdaQueryWrapper<AiModel> w = new LambdaQueryWrapper<>(); w.eq(modelType != null && !modelType.isEmpty(), AiModel::getModelType, modelType); return list(w); }
    @Override public AiModel register(AiModel model) { model.setStatus(0); save(model); return model; }
    @Override public Map<String, Object> test(Long id, String input) { Map<String, Object> r = new HashMap<>(); r.put("modelId", id); r.put("input", input); r.put("output", "Test response"); r.put("latency", 150); return r; }
    @Override public void build(Long id) { AiModel m = getById(id); m.setStatus(1); updateById(m); }
}
