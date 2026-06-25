package com.pub.supervision.service;

import java.util.Map;

public interface EffectivenessService {
    Map<String, Object> getOverview(String startDate, String endDate);
    Map<String, Object> getRanking(String startDate, String endDate);
    Map<String, Object> getDepartmentCompare(String startDate, String endDate);
}
