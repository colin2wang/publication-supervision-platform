package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.sys.SysDictType;
import com.pub.supervision.entity.sys.SysDictData;
import java.util.List;

public interface DictService extends IService<SysDictType> {
    List<SysDictType> getTypes();
    List<SysDictData> getDataByCode(String dictCode);
}
