package com.fuzhongwangcs.ssmsimple.web.service.impl;

import com.fuzhongwangcs.ssmsimple.core.cache.RedisCache;
import com.fuzhongwangcs.ssmsimple.core.generic.GenericDao;
import com.fuzhongwangcs.ssmsimple.core.generic.GenericServiceImpl;
import com.fuzhongwangcs.ssmsimple.web.dao.UserRoleMapper;
import com.fuzhongwangcs.ssmsimple.web.model.UserRole;
import com.fuzhongwangcs.ssmsimple.web.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: lazyeclipse
 * @Description: 用户Service实现类
 * @Date: 2017/5/11 16:39
 */
@Service
public class UserRoleServiceImpl extends GenericServiceImpl<UserRole, Long> implements UserRoleService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Resource
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RedisCache cache;

    @Override
    public int insert(UserRole model) {
        return userRoleMapper.insertSelective(model);
    }

    @Override
    public int update(UserRole model) {
        return userRoleMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public int delete(Long id) {
        return userRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UserRole selectById(Long id) {
        return userRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public GenericDao<UserRole, Long> getDao() {
        return userRoleMapper;
    }
}
