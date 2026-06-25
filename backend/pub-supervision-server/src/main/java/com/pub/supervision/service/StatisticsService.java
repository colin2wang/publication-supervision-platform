package com.pub.supervision.service;

import java.util.Map;

public interface StatisticsService {
    Map<String, Object> getOverview(String startDate, String endDate);
    Map<String, Object> getMerchantRisk();
    Map<String, Object> getLogisticsTrend(String startDate, String endDate);
    Map<String, Object> getAlertType(String startDate, String endDate);
}
