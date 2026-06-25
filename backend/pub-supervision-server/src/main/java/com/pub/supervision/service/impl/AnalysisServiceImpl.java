package com.pub.supervision.service.impl;

import com.pub.supervision.service.AnalysisService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Override public Map<String, Object> sentimentTrend(String startDate, String endDate) { Map<String, Object> r = new HashMap<>(); r.put("dates", List.of("2024-01-01", "2024-01-02")); r.put("positive", List.of(45, 52)); r.put("neutral", List.of(30, 28)); r.put("negative", List.of(25, 20)); return r; }
    @Override public Map<String, Object> propagation(Long opinionId) { Map<String, Object> r = new HashMap<>(); r.put("nodes", List.of()); r.put("edges", List.of()); r.put("depth", 3); return r; }
    @Override public Map<String, Object> correlation(String keyword) { Map<String, Object> r = new HashMap<>(); r.put("keyword", keyword); r.put("relatedKeywords", List.of("盗版", "侵权")); return r; }
    @Override public Map<String, Object> hotTopics(int topN) { Map<String, Object> r = new HashMap<>(); r.put("topics", List.of("盗版图书", "网络侵权")); r.put("heatScores", List.of(9850, 7620)); return r; }
    @Override public Map<String, Object> predict(String keyword) { Map<String, Object> r = new HashMap<>(); r.put("keyword", keyword); r.put("predictedHeat", 8500); r.put("trend", "rising"); return r; }
}
