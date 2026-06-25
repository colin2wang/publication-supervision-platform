package com.pub.supervision.service.impl;

import com.pub.supervision.service.StatisticsService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Override public Map<String, Object> getOverview(String startDate, String endDate) { Map<String, Object> r = new HashMap<>(); r.put("totalMerchants", 1250); r.put("totalPackages", 56800); r.put("totalAlerts", 342); r.put("handledAlerts", 298); r.put("alertHandleRate", 87.1); return r; }
    @Override public Map<String, Object> getMerchantRisk() { Map<String, Object> r = new HashMap<>(); r.put("highRisk", 12); r.put("mediumRisk", 45); r.put("lowRisk", 1193); return r; }
    @Override public Map<String, Object> getLogisticsTrend(String startDate, String endDate) { Map<String, Object> r = new HashMap<>(); r.put("dates", List.of("2024-01", "2024-02", "2024-03")); r.put("volumes", List.of(15000, 16500, 18000)); return r; }
    @Override public Map<String, Object> getAlertType(String startDate, String endDate) { Map<String, Object> r = new HashMap<>(); r.put("types", List.of("盗版", "侵权", "违规")); r.put("counts", List.of(120, 85, 137)); return r; }
}
