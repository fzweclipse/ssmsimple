package com.fuzhongwangcs.ssmsimple.web.service;

import com.fuzhongwangcs.ssmsimple.core.generic.GenericService;
import com.fuzhongwangcs.ssmsimple.web.model.Role;

import java.util.List;

/**
 * @Author: lazyeclipse
 * @Description: 角色 业务接口
 * @Date: 2017/5/11 16:38
 */
public interface RoleService extends GenericService<Role, Long> {
    /**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);
}
