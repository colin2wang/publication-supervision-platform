package com.pub.supervision.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pub.supervision.common.enums.ResultCode;
import com.pub.supervision.common.exception.BusinessException;
import com.pub.supervision.common.result.PageResult;
import com.pub.supervision.entity.sys.SysRole;
import com.pub.supervision.entity.sys.SysRoleMenu;
import com.pub.supervision.mapper.SysRoleMapper;
import com.pub.supervision.mapper.SysRoleMenuMapper;
import com.pub.supervision.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements RoleService {
    private final SysRoleMenuMapper roleMenuMapper;
    public RoleServiceImpl(SysRoleMenuMapper roleMenuMapper) { this.roleMenuMapper = roleMenuMapper; }

    @Override
    public PageResult<SysRole> page(String roleName, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<SysRole> w = new LambdaQueryWrapper<>();
        w.like(roleName != null && !roleName.isEmpty(), SysRole::getRoleName, roleName)
         .eq(status != null, SysRole::getStatus, status)
         .orderByAsc(SysRole::getRoleSort);
        Page<SysRole> p = page(new Page<>(pageNum, pageSize), w);
        return new PageResult<>(p.getRecords(), p.getTotal(), pageNum, pageSize);
    }

    @Override
    public SysRole getById(Long id) {
        SysRole r = super.getById(id);
        if (r == null) throw new BusinessException(ResultCode.NOT_FOUND);
        return r;
    }

    @Override
    public void create(SysRole role) { save(role); }
    @Override
    public void update(SysRole role) { updateById(role); }
    @Override
    public void delete(Long id) { removeById(id); }

    @Override
    @Transactional
    public void assignMenus(Long roleId, List<Long> menuIds) {
        LambdaQueryWrapper<SysRoleMenu> w = new LambdaQueryWrapper<>();
        w.eq(SysRoleMenu::getRoleId, roleId);
        roleMenuMapper.delete(w);
        for (Long menuId : menuIds) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(roleId);
            rm.setMenuId(menuId);
            roleMenuMapper.insert(rm);
        }
    }
}
