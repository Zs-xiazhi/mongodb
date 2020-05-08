package com.xiazhi.mongodb.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


/**
 * @author ZhaoShuai
 * @date Create in 2020/5/6
 **/
public enum MongoUtil {

    /**
     * 此类的一个实例
     */
    instance;

    private static MongoClient mongoClient;

    static {
        System.out.println("***********MongodbUtil初始化***************");
        String host = "192.168.226.130";
        int port = 27017;
        mongoClient = new MongoClient(host, port);
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.cursorFinalizerEnabled(true);
        builder.connectionsPerHost(300);
        builder.connectTimeout(30000);
        builder.maxWaitTime(5000);
        builder.socketTimeout(0);
        builder.threadsAllowedToBlockForConnectionMultiplier(5000);
        builder.build();
    }

    /**
     * 获取数据库
     * @param dbName
     * @return
     */
    public MongoDatabase getDatabase(String dbName) {
        Assert.hasText(dbName, "dbName con not be null");
        return mongoClient.getDatabase(dbName);
    }

    /**
     * 获取collections
     * @param database 数据库名
     * @param collection 表名
     * @return 表数据信息
     */
    public MongoCollection<Document> getCollection(String database,String collection) {
        MongoDatabase mongoDatabase = getDatabase(database);

        Assert.hasText(collection, "collection name can not be null");
        return mongoDatabase.getCollection(collection);
    }

    /**
     * 获取所有collection的名字
     * @param database 数据库名
     * @return 表名
     */
    public List<String> getCollectionsName(String database) {
        MongoDatabase mongoDatabase = getDatabase(database);
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();

        List<String> list = new ArrayList<>();
        for (String next : collectionNames) {
            list.add(next);
        }

        return list;
    }

    /**
     * 获取所有数据库名
     * @return 所有数据库名
     */
    public List<String> getDatabaseNames() {
        MongoIterable<String> databaseNames = mongoClient.listDatabaseNames();

        List<String> list = new ArrayList<>();
        for (String databaseName : databaseNames) {
            list.add(databaseName);
        }

        return list;
    }

    /**
     * 插入一条数据
     * @param db
     * @param coll
     * @param document
     */
    public void insert(String db, String coll, Document document) {
        MongoCollection<Document> collection = getCollection(db, coll);
        Assert.notNull(document, "插入数据不能为空");
        collection.insertOne(document);
    }

    /**
     * 根据主键查找
     * @param db
     * @param coll
     * @param id
     * @return
     */
    public Document findById(String db, String coll, String id) {
        MongoCollection<Document> collection = getCollection(db, coll);
        return findById(collection, id);
    }

    /**
     * 根据主键查找
     * @param collection
     * @param id
     * @return
     */
    public Document findById(MongoCollection<Document> collection, String id) {
        Assert.notNull(id, "id can not be null");
        ObjectId objectId = new ObjectId(id);
        return collection.find(Filters.eq("_id", objectId)).first();
    }

    /**
     * 条件查询
     * @param coll
     * @param filter
     * @return
     */
    public MongoCursor<Document> findByCondition(MongoCollection<Document> coll, Bson filter) {
        return coll.find(filter).cursor();
    }


}
