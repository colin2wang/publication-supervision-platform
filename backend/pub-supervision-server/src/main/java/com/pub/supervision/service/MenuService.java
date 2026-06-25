package com.pub.supervision.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pub.supervision.entity.sys.SysMenu;
import java.util.List;

public interface MenuService extends IService<SysMenu> {
    List<SysMenu> getTree();
    void create(SysMenu menu);
    void update(SysMenu menu);
    void delete(Long id);
}
