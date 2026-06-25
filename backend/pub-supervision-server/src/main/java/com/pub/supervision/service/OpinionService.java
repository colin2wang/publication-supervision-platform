package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.opi.OpiOpinion;
import com.pub.supervision.common.result.PageResult;
import java.util.List;

public interface OpinionService extends IService<OpiOpinion> {
    PageResult<OpiOpinion> page(String keyword, Integer sentiment, Integer channelType, int pageNum, int pageSize);
    OpiOpinion getById(Long id);
    void delete(Long id);
    void batchDelete(List<Long> ids);
    void mark(Long id, Integer isMarked);
}
