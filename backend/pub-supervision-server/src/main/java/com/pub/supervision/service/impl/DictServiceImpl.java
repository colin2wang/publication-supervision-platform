package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.entity.sys.SysDictType;
import com.pub.supervision.entity.sys.SysDictData;
import com.pub.supervision.mapper.SysDictTypeMapper;
import com.pub.supervision.mapper.SysDictDataMapper;
import com.pub.supervision.service.DictService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements DictService {
    private final SysDictDataMapper dictDataMapper;
    public DictServiceImpl(SysDictDataMapper dictDataMapper) { this.dictDataMapper = dictDataMapper; }

    @Override
    public List<SysDictType> getTypes() {
        LambdaQueryWrapper<SysDictType> w = new LambdaQueryWrapper<>();
        w.eq(SysDictType::getStatus, 1);
        return list(w);
    }

    @Override
    public List<SysDictData> getDataByCode(String dictCode) {
        LambdaQueryWrapper<SysDictData> w = new LambdaQueryWrapper<>();
        w.eq(SysDictData::getDictType, dictCode).eq(SysDictData::getStatus, 1).orderByAsc(SysDictData::getDictSort);
        return dictDataMapper.selectList(w);
    }
}
