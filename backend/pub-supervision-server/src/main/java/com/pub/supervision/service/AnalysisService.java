package com.pub.supervision.service;

import java.util.Map;

public interface AnalysisService {
    Map<String, Object> sentimentTrend(String startDate, String endDate);
    Map<String, Object> propagation(Long opinionId);
    Map<String, Object> correlation(String keyword);
    Map<String, Object> hotTopics(int topN);
    Map<String, Object> predict(String keyword);
}
