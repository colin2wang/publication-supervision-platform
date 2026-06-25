package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.entity.sys.SysMenu;
import com.pub.supervision.mapper.SysMenuMapper;
import com.pub.supervision.service.MenuService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements MenuService {
    @Override
    public List<SysMenu> getTree() {
        LambdaQueryWrapper<SysMenu> w = new LambdaQueryWrapper<>();
        w.orderByAsc(SysMenu::getOrderNum);
        return buildTree(list(w), 0L);
    }
    private List<SysMenu> buildTree(List<SysMenu> menus, Long parentId) {
        return menus.stream().filter(m -> parentId.equals(m.getParentId()))
                .peek(m -> m.setChildren(buildTree(menus, m.getId())))
                .collect(Collectors.toList());
    }
    @Override
    public void create(SysMenu menu) { save(menu); }
    @Override
    public void update(SysMenu menu) { updateById(menu); }
    @Override
    public void delete(Long id) { removeById(id); }
}
