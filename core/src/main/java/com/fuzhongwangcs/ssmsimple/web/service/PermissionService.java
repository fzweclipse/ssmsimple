package com.fuzhongwangcs.ssmsimple.web.service;

import com.fuzhongwangcs.ssmsimple.core.generic.GenericService;
import com.fuzhongwangcs.ssmsimple.web.model.Permission;

import java.util.List;

/**
 * @Author: lazyeclipse
 * @Description: 权限 业务接口
 * @Date: 2017/5/11 16:38
 */
public interface PermissionService extends GenericService<Permission, Long> {

    /**
     * 通过角色id 查询角色 拥有的权限
     * 
     * @param roleId
     * @return
     */
    List<Permission> selectPermissionsByRoleId(Long roleId);

}
