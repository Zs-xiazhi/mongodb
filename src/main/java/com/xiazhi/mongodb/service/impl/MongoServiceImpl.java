package com.xiazhi.mongodb.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiazhi.mongodb.bean.Book;
import com.xiazhi.mongodb.service.MongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author ZhaoShuai
 * @date Create in 2020/5/8
 **/
@Service
@Slf4j
public class MongoServiceImpl implements MongoService<Book> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Book book) {
        log.debug("要保存的对象[{}]", book.toString());
        mongoTemplate.save(book);
    }

    @Override
    public void delete(Long id) {
        log.debug("要删除的对象id:[{}]", id);
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Book.class);
    }

    @Override
    public Long update(Book book) {
        Query query = new Query(Criteria.where("id").is(book.getId()));
        Update update = new Update().set("name", book.getName()).set("price", book.getPrice()).set("content", book.getContent());
        UpdateResult result = mongoTemplate.updateFirst(query, update, Book.class);

        return Objects.isNull(result) ? 0 : result.getMatchedCount();
    }

    @Override
    public Book queryForOne(Book book) {
        Query query = new Query(Criteria.where("name").is(book.getName()));
        return mongoTemplate.findOne(query, Book.class);
    }
}
