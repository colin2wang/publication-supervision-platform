package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.sys.SysDepartment;
import java.util.List;

public interface DepartmentService extends IService<SysDepartment> {
    List<SysDepartment> getTree();
    void create(SysDepartment dept);
    void update(SysDepartment dept);
    void delete(Long id);
}
