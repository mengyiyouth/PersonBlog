package com.mengyi.dao;

import com.mengyi.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mengyiyouth
 * @date 2020/5/26
 **/
@Mapper
@Repository
public interface TypeDao {

    Integer saveType(Type type);

    Type getType(Long id);

    List<Type> getAllType();

    List<Type> getAllTypeAndBlog();

    Type getTypeByName(String name);

    int updateType(Type type);

    void deleteType(Long id);



}
