package com.pub.supervision.service;

import java.util.Map;

public interface WorkbenchService {
    Map<String, Object> getStats(Long userId);
    java.util.List<Map<String, Object>> getTodoList(Long userId);
}
