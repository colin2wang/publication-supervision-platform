package com.pub.supervision.service.impl;

import com.pub.supervision.service.EffectivenessService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EffectivenessServiceImpl implements EffectivenessService {
    @Override public Map<String, Object> getOverview(String startDate, String endDate) { Map<String, Object> r = new HashMap<>(); r.put("totalOpinions", 12500); r.put("handledOpinions", 11200); r.put("handleRate", 89.6); r.put("avgResponseTime", "2.5h"); r.put("satisfactionScore", 4.2); return r; }
    @Override public Map<String, Object> getRanking(String startDate, String endDate) { Map<String, Object> r = new HashMap<>(); r.put("departments", List.of("出版科", "版权科", "执法科")); r.put("scores", List.of(95, 88, 82)); return r; }
    @Override public Map<String, Object> getDepartmentCompare(String startDate, String endDate) { Map<String, Object> r = new HashMap<>(); r.put("departments", List.of("出版科", "版权科", "执法科")); r.put("handled", List.of(4500, 3800, 2900)); r.put("avgTime", List.of(1.5, 2.8, 3.2)); return r; }
}
