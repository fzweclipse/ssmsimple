package com.fuzhongwangcs.ssmsimple.web.service.impl;

import com.fuzhongwangcs.ssmsimple.core.cache.RedisCache;
import com.fuzhongwangcs.ssmsimple.core.generic.GenericDao;
import com.fuzhongwangcs.ssmsimple.core.generic.GenericServiceImpl;
import com.fuzhongwangcs.ssmsimple.web.dao.UserMapper;
import com.fuzhongwangcs.ssmsimple.web.model.User;
import com.fuzhongwangcs.ssmsimple.web.model.UserExample;
import com.fuzhongwangcs.ssmsimple.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lazyeclipse
 * @Description: 用户Service实现类
 * @Date: 2017/5/11 16:39
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Resource
    private UserMapper userMapper;
    @Autowired
    private RedisCache cache;

    @Override
    public int insert(User model) {
        return userMapper.insertSelective(model);
    }

    @Override
    public int update(User model) {
        return userMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User authentication(User user) {
        return userMapper.authentication(user);
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public GenericDao<User, Long> getDao() {
        return userMapper;
    }

    @Override
    public User selectByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);

        String cache_key = RedisCache.CAHCENAME + "|selectByUsername|" + username;
        List<User> list = cache.getListCache(cache_key, User.class);
        if (list != null) {
            LOG.info("get cache with key:" + cache_key);
        } else {
            // 缓存中没有再去数据库取，并插入缓存（缓存时间为60秒）
            list = userMapper.selectByExample(example);
            cache.putListCacheWithExpireTime(cache_key, list, RedisCache.CAHCETIME);
            LOG.info("put cache with key:" + cache_key);
        }

        //final List<User> list = userMapper.selectByExample(example);
        return list.get(0);
    }

}
