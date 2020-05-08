package com.xiazhi.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.xiazhi.mongodb.bean.Book;
import com.xiazhi.mongodb.service.MongoService;
import com.xiazhi.mongodb.util.MongoUtil;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.math.BigDecimal;

@SpringBootTest
class MongodbApplicationTests {

    @Autowired
    private MongoService<Book> mongoService;

    @Test
    void contextLoads() {
        MongoDatabase test = MongoUtil.instance.getDatabase("test");
        System.out.println(test);

        MongoCollection<Document> collections = MongoUtil.instance.getCollection("test", "coll");
        FindIterable<Document> documents = collections.find();
        for (Document document : documents) {
            System.out.println(document);
        }

        Document document = MongoUtil.instance.findById(collections, "5eb230a80a6d7da784ff2c83");
        System.out.println(document);

        Document insert = new Document();
        insert.put("name", "lisi");
        insert.put("age", 18);

        MongoUtil.instance.insert("test", "coll", insert);

        BsonDocument filter = new BsonDocument();
        filter.put("name", new BsonString("lisi"));
        MongoCursor<Document> cursor = MongoUtil.instance.findByCondition(collections, filter);
        while (cursor.hasNext()) {
            Document next = cursor.next();
            System.out.println(next);
        }
    }

    @Test
    void test() {
        mongoService.save(new Book().setId(1L).setName("java").setPrice(BigDecimal.valueOf(50.55)).setContent("hello java"));
        mongoService.save(new Book().setId(2L).setName("c").setPrice(BigDecimal.valueOf(55.55)).setContent("hello c"));
        mongoService.update(new Book().setId(1L).setName("python").setContent("hello python").setPrice(BigDecimal.valueOf(60.01)));
        Book book = mongoService.queryForOne(new Book().setName("python"));
        System.out.println(book.toString());
        mongoService.delete(1L);

    }
}
