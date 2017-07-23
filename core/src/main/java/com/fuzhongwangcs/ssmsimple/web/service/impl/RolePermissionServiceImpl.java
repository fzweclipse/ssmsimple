package com.fuzhongwangcs.ssmsimple.web.service.impl;

import com.fuzhongwangcs.ssmsimple.core.generic.GenericDao;
import com.fuzhongwangcs.ssmsimple.core.generic.GenericServiceImpl;
import com.fuzhongwangcs.ssmsimple.web.dao.RolePermissionMapper;
import com.fuzhongwangcs.ssmsimple.web.model.RolePermission;
import com.fuzhongwangcs.ssmsimple.web.service.RolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RolePermissionServiceImpl extends GenericServiceImpl<RolePermission, Long> implements RolePermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public GenericDao<RolePermission, Long> getDao() {
        return rolePermissionMapper;
    }
}