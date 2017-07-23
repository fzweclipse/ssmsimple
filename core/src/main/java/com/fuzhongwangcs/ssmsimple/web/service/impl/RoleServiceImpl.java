package com.fuzhongwangcs.ssmsimple.web.service.impl;

import com.fuzhongwangcs.ssmsimple.core.cache.RedisCache;
import com.fuzhongwangcs.ssmsimple.core.generic.GenericDao;
import com.fuzhongwangcs.ssmsimple.core.generic.GenericServiceImpl;
import com.fuzhongwangcs.ssmsimple.web.dao.RoleMapper;
import com.fuzhongwangcs.ssmsimple.web.model.Role;
import com.fuzhongwangcs.ssmsimple.web.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lazyeclipse
 * @Description: 角色Service实现类
 * @Date: 2017/5/11 16:39
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements RoleService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Resource
    private RoleMapper roleMapper;
    @Autowired
    private RedisCache cache;

    @Override
    public GenericDao<Role, Long> getDao() {
        return roleMapper;
    }

    @Override
    public List<Role> selectRolesByUserId(Long userId) {
        String cache_key = RedisCache.CAHCENAME + "|selectRolesByUserId|" + userId;
        List<Role> result_cache = cache.getListCache(cache_key, Role.class);
        if (result_cache != null) {
            LOG.info("get cache with key:" + cache_key);
        } else {
            // 缓存中没有再去数据库取，并插入缓存（缓存时间为60秒）
            result_cache = roleMapper.selectRolesByUserId(userId);
            cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
            LOG.info("put cache with key:" + cache_key);
            return result_cache;
        }
        return result_cache;
        //return roleMapper.selectRolesByUserId(userId);
    }

}
