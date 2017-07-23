package com.fuzhongwangcs.ssmsimple.web.service.impl;

import com.fuzhongwangcs.ssmsimple.core.cache.RedisCache;
import com.fuzhongwangcs.ssmsimple.core.generic.GenericDao;
import com.fuzhongwangcs.ssmsimple.core.generic.GenericServiceImpl;
import com.fuzhongwangcs.ssmsimple.web.dao.PermissionMapper;
import com.fuzhongwangcs.ssmsimple.web.model.Permission;
import com.fuzhongwangcs.ssmsimple.web.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lazyeclipse
 * @Description: 权限Service实现类
 * @Date: 2017/5/11 16:39
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements PermissionService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Resource
    private PermissionMapper permissionMapper;
    @Autowired
    private RedisCache cache;

    @Override
    public GenericDao<Permission, Long> getDao() {
        return permissionMapper;
    }

    @Override
    public List<Permission> selectPermissionsByRoleId(Long roleId) {
        String cache_key = RedisCache.CAHCENAME + "|selectPermissionsByRoleId|" + roleId;
        List<Permission> result_cache = cache.getListCache(cache_key, Permission.class);
        if (result_cache != null) {
            LOG.info("get cache with key:" + cache_key);
        } else {
            // 缓存中没有再去数据库取，并插入缓存（缓存时间为60秒）
            result_cache = permissionMapper.selectPermissionsByRoleId(roleId);
            if (result_cache.size() > 0) {
                LOG.info("put cache with key:" + cache_key);
                cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
                return result_cache;
            }
        }
        return result_cache;
        //return permissionMapper.selectPermissionsByRoleId(roleId);
    }
}
