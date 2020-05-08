package com.xiazhi.mongodb.service;

/**
 * @author 赵帅
 * @date Create in 2020/5/8
 **/
public interface MongoService<T> {

    void save(T t);

    void delete(Long id);

    Long update(T t);

    T queryForOne(T t);
}
