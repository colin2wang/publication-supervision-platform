package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.opi.OpiOpinion;
import com.pub.supervision.mapper.OpiOpinionMapper;
import com.pub.supervision.service.OpinionService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OpinionServiceImpl extends ServiceImpl<OpiOpinionMapper, OpiOpinion> implements OpinionService {
    @Override public PageResult<OpiOpinion> page(String keyword, Integer sentiment, Integer channelType, int pageNum, int pageSize) {
        LambdaQueryWrapper<OpiOpinion> w = new LambdaQueryWrapper<>();
        w.like(keyword != null && !keyword.isEmpty(), OpiOpinion::getTitle, keyword)
         .eq(sentiment != null, OpiOpinion::getSentiment, sentiment)
         .eq(channelType != null, OpiOpinion::getChannelType, channelType).orderByDesc(OpiOpinion::getCreatedAt);
        Page<OpiOpinion> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }
    @Override public OpiOpinion getById(Long id) { OpiOpinion o = super.getById(id); if (o == null) throw new BusinessException(ResultCode.NOT_FOUND); return o; }
    @Override public void delete(Long id) { removeById(id); }
    @Override public void batchDelete(List<Long> ids) { removeByIds(ids); }
    @Override public void mark(Long id, Integer isMarked) { OpiOpinion o = new OpiOpinion(); o.setId(id); o.setIsMarked(isMarked); updateById(o); }
}
