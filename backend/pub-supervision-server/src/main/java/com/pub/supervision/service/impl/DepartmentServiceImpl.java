package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.entity.sys.SysDepartment;
import com.pub.supervision.mapper.SysDepartmentMapper;
import com.pub.supervision.service.DepartmentService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements DepartmentService {
    @Override
    public List<SysDepartment> getTree() {
        LambdaQueryWrapper<SysDepartment> w = new LambdaQueryWrapper<>();
        w.orderByAsc(SysDepartment::getSort);
        return buildTree(list(w), 0L);
    }
    private List<SysDepartment> buildTree(List<SysDepartment> depts, Long parentId) {
        return depts.stream().filter(d -> parentId.equals(d.getParentId()))
                .peek(d -> d.setChildren(buildTree(depts, d.getId())))
                .collect(Collectors.toList());
    }
    @Override
    public void create(SysDepartment dept) { save(dept); }
    @Override
    public void update(SysDepartment dept) { updateById(dept); }
    @Override
    public void delete(Long id) { removeById(id); }
}
