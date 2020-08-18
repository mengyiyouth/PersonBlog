package com.mengyi.service;

import com.mengyi.annotation.ClearRedisCache;
import com.mengyi.entity.Type;
import com.mengyi.service.impl.BlogServiceImpl;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
public interface TypeService {

    @ClearRedisCache(cascade = BlogServiceImpl.class)
    Integer saveType(Type type);

    @Cacheable(cacheNames = "cache")
    Type getType(Long id);

    @Cacheable(cacheNames = "cache")
    List<Type>getAllType();

    @Cacheable(cacheNames = "cache")
    List<Type>getAllTypeAndBlog();

    @Cacheable(cacheNames = "cache")
    Type getTypeByName(String name);

    @ClearRedisCache(cascade = BlogServiceImpl.class)
    int updateType(Type type);

    @ClearRedisCache(cascade = BlogServiceImpl.class)
    void deleteType(Long id);

}
