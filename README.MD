# MongoDB
## 什么是mongodb?
　　mongodb是一个基于分布式文件存储的数据库。mongodb是NoSQL数据库，也就是非关系型数据库，内存型数据库。  
## mongodb和redis的区别？
   在之前学习过redis, redis也是内存型NoSql数据库，这与mongodb非常相似，两者之间有什么区别呢？  
   1. 存储类型不同。  
       redis是典型的key-value型数据库。redis是key-value型的存储系统，也就是数据必须有key值，一个key对应一个value，redis支持的value的数据类型有
   String(字符), set(集合), list(链表), zset(有序集合), hash(哈希)类型。  
       mongodb是文档性数据库。mongodb中的数据是以文档的形式存在的, 文档类型包括json, xml, bson等。mongodb不需要key值, 数据已集合的形式存在数据库中。  
   每一个库可以有多个集合（collection）, 每一个集合就相当于是一个数据库表, mongodb支持索引, 默认每一个文档的_id字段为主键。  
   2. 内存空间的大小。  
       redis在2.0版本之后增加了自己的vm特性, 突破了物理内存的限制, 可以对数据设置过期时间。  
       mongodb依赖操作系统vm做内存管理, 比较吃内存, 采用镜像文件存储。  
   3. 数据量大小。  
       redis适合数据量比较小的操作和运算。    
       mongodb适合数据量比较大的服务, 主要解决海量数据访问效率问题。   
   4. 数据一致性（事物支持）    
       redis事物支持比较弱, 只能保证事物中的操作按顺序执行。  
       mongoDB不支持事物。靠客户端自身保证。  
   5. 可操作性  
       redis支持数据类型多, 但是支持的操作比较少,主要通过key来对数据进行操作。  
       mongodb内置丰富的数据表达式、索引、函数。最类似关系型数据库, 支持丰富的查询语言。  
   6. 可用性  
       redis依赖客户端来实现分布式读写，主从复制时，每次从节点重新连接主节点都要依赖整个快照，无增量复制，不支持自动sharding,需要依赖程序设定一致hash机制。  
       mongodb支持master-slave,replicaset（内部采用paxos选举算法，自动故障恢复）,auto sharding机制，对客户端屏蔽了故障转移和切分机制。  
       
       mongodb优于redis  
   7. 可靠性  
       mongodb自1.8版本之后, 采用binlog方式(mysql采用同样方式)支持持久化,增加可靠性。  
       redis持久化方式：  
           7.1 RDB: 对内存中的数据进行数据库状态进行快照  
           7.2 AOF: 把每条命令都写入文件, 类似mysql的binlog方式。  
           redis主要通过RDB数据快照的方式来持久化，通过AOF来增强可靠性，但是增强可靠性的同时会影响访问性能。  
       mongodb优于redis    
## MongoDB的安装：
   新建linux虚拟机。  
   执行命令：  
   `curl -O https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-3.0.6.tgz`   
   `tar -zxvf mongodb-linux-x86_64-3.0.6.tgz`    
   `mv mongodb-linux-x86_64-3.0.6/ /usr/local/mongodb`    
   加mongodb的环境变量，类似java配置环境变量：  
   `vi /etc/profile`   
   添加：   
   `MONGODB_HOME=/usr/local/mongodb`   
   `PATH=$PATH:$MONGODB_HOME/bin`   
   保存退出。  
   `source /etc/profile`  
   创建数据库目录。  
   mkdir -p /data/db   
   mongodb的数据库目录默认路径为/data/db, 但是不会自己创建，需要手动创建。
## Mongodb启动：
   可以直接在mongodb目录下的bin目录下运行./mongod 来启动mongodb数据库, 因为配置了环境变量的关系，也可以直接执行mongod来启动。
   启动成功后：
   ~~~
   2020-04-24T04:34:40.903-0700 W -        [initandlisten] Detected unclean shutdown - /data/db/mongod.lock is not empty.
   2020-04-24T04:34:40.913-0700 I JOURNAL  [initandlisten] journal dir=/data/db/journal
   2020-04-24T04:34:40.914-0700 I JOURNAL  [initandlisten] recover begin
   2020-04-24T04:34:40.914-0700 I JOURNAL  [initandlisten] recover lsn: 0
   2020-04-24T04:34:40.914-0700 I JOURNAL  [initandlisten] recover /data/db/journal/j._0
   2020-04-24T04:34:40.915-0700 I JOURNAL  [initandlisten] recover cleaning up
   2020-04-24T04:34:40.915-0700 I JOURNAL  [initandlisten] removeJournalFiles
   2020-04-24T04:34:40.917-0700 I JOURNAL  [initandlisten] recover done
   2020-04-24T04:34:40.964-0700 I JOURNAL  [durability] Durability thread started
   2020-04-24T04:34:40.964-0700 I CONTROL  [initandlisten] MongoDB starting : pid=4042 port=27017 dbpath=/data/db 64-bit host=moggledb
   2020-04-24T04:34:40.964-0700 I CONTROL  [initandlisten] ** WARNING: You are running this process as the root user, which is not recommended.
   2020-04-24T04:34:40.964-0700 I CONTROL  [initandlisten]
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten]
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten] ** WARNING: /sys/kernel/mm/transparent_hugepage/enabled is 'always'.
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten] **        We suggest setting it to 'never'
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten]
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten] ** WARNING: /sys/kernel/mm/transparent_hugepage/defrag is 'always'.
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten] **        We suggest setting it to 'never'
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten]
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten] db version v3.0.6
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten] git version: 1ef45a23a4c5e3480ac919b28afcba3c615488f2
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten] build info: Linux build6.ny.cbi.10gen.cc 2.6.32-431.3.1.el6.x86_64 #1 SMP Fri Jan 3 21:39:27 UTC 2014 x86_64 BOOST_LIB_VERSION=1_49
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten] allocator: tcmalloc
   2020-04-24T04:34:40.965-0700 I CONTROL  [initandlisten] options: {}
   2020-04-24T04:34:40.970-0700 I JOURNAL  [journal writer] Journal writer thread started
   2020-04-24T04:34:40.976-0700 I NETWORK  [initandlisten] waiting for connections on port 27017
   ~~~
   mongodb的默认端口是27017。
# MongoDB的基本使用：
在bin目录下，执行mongo脚本，可以开启mongo的shell命令行。  
`./mongo`
~~~
MongoDB shell version: 3.0.6
connecting to: test
Server has startup warnings:
2020-04-24T04:43:56.723-0700 I CONTROL  [initandlisten] ** WARNING: You are running this process as the root user, which is not recommended.
2020-04-24T04:43:56.723-0700 I CONTROL  [initandlisten]
2020-04-24T04:43:56.723-0700 I CONTROL  [initandlisten]
2020-04-24T04:43:56.723-0700 I CONTROL  [initandlisten] ** WARNING: /sys/kernel/mm/transparent_hugepage/enabled is 'always'.
2020-04-24T04:43:56.723-0700 I CONTROL  [initandlisten] **        We suggest setting it to 'never'
2020-04-24T04:43:56.723-0700 I CONTROL  [initandlisten]
2020-04-24T04:43:56.723-0700 I CONTROL  [initandlisten] ** WARNING: /sys/kernel/mm/transparent_hugepage/defrag is 'always'.
2020-04-24T04:43:56.723-0700 I CONTROL  [initandlisten] **        We suggest setting it to 'never'
2020-04-24T04:43:56.723-0700 I CONTROL  [initandlisten]
>
~~~
数据库：  
 创建数据库：`use studydb`  
 ~~~
> use studydb
switched to db studydb
 ~~~
如果数据库不存在，mongodb会自动创建数据库。
 查询当前数据库：`db`  
 ~~~
> db
studydb
 ~~~
查询所有数据库：`show dbs`
~~~
> show dbs
local  0.078GB
~~~
上面并没有我们的数据库，要想显示它，需要先往里面填加数据。mongodb中，集合只有在插入数据后才会真正被创建。也就是说创建集合后再插入一个文档，集合才会被创建。  
新增数据：`db.bookcoll.insert({"name":"mongodb","content":"hello world!"})`  
 ~~~
> db.bookcoll.insert({"name":"mongodb","content":"hello world!"})
WriteResult({ "nInserted" : 1 })
 ~~~
db表示当前数据库,bookcoll表示集合, 如果没有会自动创建。
~~~
> show dbs
local  0.078GB
study  0.078GB
~~~
创建集合：`db.createCollection(name,options)`
**name: 集合名称(必填)  
options: 可配置参数(选填)**  
~~~
options取值：
    capped:  布尔型。如果为true，则创建固定集合。固定集合是有固定大小的集合，当达到最大大小时，它会自动覆盖最早的文档。当为true时，必须指定size参数。    
    autoIndexId： 布尔型。如果为true，自动在_id字段创建索引。默认为false。  
    size：  数值型。为固定集合指定一个最大值，以千字节(KB)记。  
    max: 数值型。指定固定集合中包含文档的最大数量。  
~~~
mongodb在插入文档时，先检查固定集合的size字段，在检查max字段。  
~~~
> db.createCollection("java")
{ "ok" : 1 }
~~~
查看所有集合：`show collections`或`show tables`
~~~
> show collections
bookcoll
java
system.indexes
> show tables
bookcoll
java
system.indexes
>
~~~
创建固定集合：`db.createCollection("books", {capped:true, autoIndexId:true, size:1000, max:5})`  
~~~
> db.createCollection("book",{capped:true, autoIndexId:true, size:1000, max:5})
{ "ok" : 1 }
~~~
插入文档：`db.book.insert()`或`db.book.save()`  
可以通过insert或save方法向集合中插入文档。db当前数据库，book：要插入数据的集合名。还可以通过定义变量的方式插入。  
之前创建了一个固定集合。设置的最大值是5，现在插入五条数据测试。
~~~
> db.book.insert({"name":"java"})
WriteResult({ "nInserted" : 1 })
> db.book.save({"price":100.00})
WriteResult({ "nInserted" : 1 })
> db.book.save({"age":10})
WriteResult({ "nInserted" : 1 })
> document={"phone":"10086"}
{ "phone" : "10086" }
> db.book.insert(document)
WriteResult({ "nInserted" : 1 })
> db.book.insert({"content":"hello world"})
WriteResult({ "nInserted" : 1 })
~~~
查看插入的数据：`db.book.find()`
~~~
{ "_id" : ObjectId("5ea2e439383590406bd4e0f7"), "name" : "java" }
{ "_id" : ObjectId("5ea2e454383590406bd4e0f8"), "price" : 100 }
{ "_id" : ObjectId("5ea2e483383590406bd4e0f9"), "age" : 10 }
{ "_id" : ObjectId("5ea2e4f0383590406bd4e0fa"), "phone" : "10086" }
{ "_id" : ObjectId("5ea2e554383590406bd4e0fb"), "content" : "hello world" }
~~~
_id字段为mongodb自动为文档生成的主键。在mongodb中，每一个database就相当于关系型数据库中的database。
collection就相当于关系型数据库中的表table。而每一条文档就是行row，文档中的字段就是列column。在mongodb中，并不要求每一行的每一列都相同。
无论是列名还是列的数据格式，都可以不同。  
上面插入的是一个固定集合，我们设置的最大数是五个，现在已经五个了，我们再插入一条记录。
~~~
> db.book.insert({"price":"50"})
WriteResult({ "nInserted" : 1 })
> db.book.find()
{ "_id" : ObjectId("5ea2e454383590406bd4e0f8"), "price" : 100 }
{ "_id" : ObjectId("5ea2e483383590406bd4e0f9"), "age" : 10 }
{ "_id" : ObjectId("5ea2e4f0383590406bd4e0fa"), "phone" : "10086" }
{ "_id" : ObjectId("5ea2e554383590406bd4e0fb"), "content" : "hello world" }
{ "_id" : ObjectId("5ea2e725383590406bd4e0fc"), "price" : "50" }
~~~
可以看到, 超过最大数量时，会自动覆盖掉最早的数据，而且即使字段名一样时，字段的值也可以不相同。再mongodb中，每一条文档就是单独的行，与其他的行的字段类型等没有关连。
更新文档： `db.book.update()`或`db.book.save()`   
mongodb中通过update()方法或save()方法来更新。  
**update方法:** `db.book.update(<query>,<update>,{upsert: <boolean>, multi: <boolean>, writeConcern: <document>})`     
~~~
方法参数介绍：
    query: 类似sql语句中的where后的条件  
    update: 更新的对象，类似与sql update语句中set后的属性
    upsert： 当更新的对象找不到时，是否插入数据，默认false。
    multi：mongodb默认只更新找到的第一条记录，如果改为true，则更改  
    writeConcern: 抛出异常级别
~~~
~~~
> db.book.find()
{ "_id" : ObjectId("5ea2e454383590406bd4e0f8"), "price" : 100 }
{ "_id" : ObjectId("5ea2e483383590406bd4e0f9"), "age" : 10 }
{ "_id" : ObjectId("5ea2e4f0383590406bd4e0fa"), "phone" : "10086" }
{ "_id" : ObjectId("5ea2e554383590406bd4e0fb"), "content" : "hello world" }
{ "_id" : ObjectId("5ea2e725383590406bd4e0fc"), "price" : "50" }
> db.book.update({"price":"50"},{$set:{"price":"30"}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.book.find()
{ "_id" : ObjectId("5ea2e454383590406bd4e0f8"), "price" : 100 }
{ "_id" : ObjectId("5ea2e483383590406bd4e0f9"), "age" : 10 }
{ "_id" : ObjectId("5ea2e4f0383590406bd4e0fa"), "phone" : "10086" }
{ "_id" : ObjectId("5ea2e554383590406bd4e0fb"), "content" : "hello world" }
{ "_id" : ObjectId("5ea2e725383590406bd4e0fc"), "price" : "30" }
>
~~~
加上可选参数:
~~~
> db.book.update({"price":100},{$set:{"price":"30"}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.book.find()
{ "_id" : ObjectId("5ea2e454383590406bd4e0f8"), "price" : "30" }
{ "_id" : ObjectId("5ea2e483383590406bd4e0f9"), "age" : 10 }
{ "_id" : ObjectId("5ea2e4f0383590406bd4e0fa"), "phone" : "10086" }
{ "_id" : ObjectId("5ea2e554383590406bd4e0fb"), "content" : "hello world" }
{ "_id" : ObjectId("5ea2e725383590406bd4e0fc"), "price" : "30" }
> db.book.update({"price":"30"},{$set:{"price":"50"}},{multi:true})
WriteResult({ "nMatched" : 2, "nUpserted" : 0, "nModified" : 2 })
> db.book.find()
{ "_id" : ObjectId("5ea2e454383590406bd4e0f8"), "price" : "50" }
{ "_id" : ObjectId("5ea2e483383590406bd4e0f9"), "age" : 10 }
{ "_id" : ObjectId("5ea2e4f0383590406bd4e0fa"), "phone" : "10086" }
{ "_id" : ObjectId("5ea2e554383590406bd4e0fb"), "content" : "hello world" }
{ "_id" : ObjectId("5ea2e725383590406bd4e0fc"), "price" : "50" }
~~~
修改时, {$set:{}},修改的内容的key必须是当前记录内已经有的key，如果没有就会修改失败，不能修改已有的子段。就像再mysql中update命令一样。    
`update table set a=?,b=? where c=?`  
条件字段必须存在, set的字段也必须是已有字段，不能是不存在的字段。  
**save方法：通过传入的文档来替换已有的文档** `db.book.save(<documemt>,{writeConcern:<document>})`
~~~
> db.book.save({ "_id" : ObjectId("5ea2e483383590406bd4e0f9"), "age":"50" })
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.book.find().pretty()
{ "_id" : ObjectId("5ea2e454383590406bd4e0f8"), "price" : "50" }
{ "_id" : ObjectId("5ea2e483383590406bd4e0f9"), "age" : "50" }
{ "_id" : ObjectId("5ea2e4f0383590406bd4e0fa"), "phone" : "10086" }
{ "_id" : ObjectId("5ea2e554383590406bd4e0fb"), "content" : "hello world" }
{ "_id" : ObjectId("5ea2e725383590406bd4e0fc"), "price" : "50" }
~~~
我们通过修改了{ "_id" : ObjectId("5ea2e483383590406bd4e0f9")}的整个文档，同样，修改时只能修改已有字段，不能新增字段。  
查询文档：`db.book.find(query,projection)`  
~~~
query: 可选搜索条件
projection: 使用投影操作符返回指定的键值。
~~~
首先插入一条数据：
~~~
> show collections
book
bookcoll
java
system.indexes
> db.bookcoll.find()
{ "_id" : ObjectId("5ea2d99b383590406bd4e0f6"), "name" : "mongodb", "content" : "hello world!" }
> db.bookcoll.insert({"name":"java","size":"30","content":"hello world","price":"100"})
WriteResult({ "nInserted" : 1 })
> db.bookcoll.find({"name":"java"},{"name":1,"price":1,"content":1})
{ "_id" : ObjectId("5ea433ede672477b2e10d90d"), "name" : "java", "content" : "hello world", "price" : "100" }
~~~
projection 指定要返回的键。用法：
~~~
db.collection.find(query,{key1:1,key2:1})
当值为1时返回，指定的键,返回key为1的键，不返回其他键，当key为0时，返回其他键,不返回指定键。
要么都为1，要么都为0，不能混用。
正例：db.collection.find(query,{key1:1,key2:1})或db.collection.find(query,{key1:0,key2:0})
反例：db.collection.find(query,{key1:1,key2:0})
~~~
此外，查询条件query还支持or语句\and语句\大于\小于等等。
~~~
db.collection.find({$or:[{"content":"hello world!"},{"name":"java"}]})
> db.bookcoll.find()
{ "_id" : ObjectId("5ea2d99b383590406bd4e0f6"), "name" : "mongodb", "content" : "hello world!" }
{ "_id" : ObjectId("5ea433ede672477b2e10d90d"), "name" : "java", "size" : "30", "content" : "hello world", "price" : "100" }
> db.bookcoll.find({$or:[{"content":"hello world!"},{"name":"java"}]})
{ "_id" : ObjectId("5ea2d99b383590406bd4e0f6"), "name" : "mongodb", "content" : "hello world!" }
{ "_id" : ObjectId("5ea433ede672477b2e10d90d"), "name" : "java", "size" : "30", "content" : "hello world", "price" : "100" }
该语句等价与sql：select * from bookcoll where name = 'java' or content = 'hello world!'
~~~
删除一个文档：`db.collection.remove(query,justOne)`
~~~
query： 搜索条件
justOne: 可选参数，如果设为true，或1，则只删除找到的第一条文档，默认为false，删除所有匹配条件的文档。
~~~
**注意：固定集合不能删除记录，但可以删除整个集合重建**
~~~
> db.bookcoll.find()
{ "_id" : ObjectId("5ea2d99b383590406bd4e0f6"), "name" : "mongodb", "content" : "hello world!" }
{ "_id" : ObjectId("5ea433ede672477b2e10d90d"), "name" : "java", "size" : "30", "content" : "hello world", "price" : "100" }
> db.bookcoll.remove({"name":"mongodb"})
WriteResult({ "nRemoved" : 1 })
> db.bookcoll.find()
{ "_id" : ObjectId("5ea433ede672477b2e10d90d"), "name" : "java", "size" : "30", "content" : "hello world", "price" : "100" }
> db.bookcoll.insert({"name":"mongodb","price":"100","content":"hello mongodb"})
WriteResult({ "nInserted" : 1 })
> db.bookcoll.insert({"name":"python","price":"100","content":"hello python"})
WriteResult({ "nInserted" : 1 })
> db.bookcoll.find()
{ "_id" : ObjectId("5ea433ede672477b2e10d90d"), "name" : "java", "size" : "30", "content" : "hello world", "price" : "100" }
{ "_id" : ObjectId("5ea43a99e672477b2e10d90e"), "name" : "mongodb", "price" : "100", "content" : "hello mongodb" }
{ "_id" : ObjectId("5ea43ab1e672477b2e10d90f"), "name" : "python", "price" : "100", "content" : "hello python" }
删除一条
> db.bookcoll.remove({"price":"100"},true)
WriteResult({ "nRemoved" : 1 })
> db.bookcoll.find()
{ "_id" : ObjectId("5ea43a99e672477b2e10d90e"), "name" : "mongodb", "price" : "100", "content" : "hello mongodb" }
{ "_id" : ObjectId("5ea43ab1e672477b2e10d90f"), "name" : "python", "price" : "100", "content" : "hello python" }
满足条件全部删除
> db.bookcoll.remove({"price":"100"})
WriteResult({ "nRemoved" : 2 })
> db.bookcoll.find()
>
> db.bookcoll.insert({"name":"mongodb","price":"100","content":"hello mongodb"})
WriteResult({ "nInserted" : 1 })
> db.bookcoll.insert({"name":"python","price":"100","content":"hello python"})
WriteResult({ "nInserted" : 1 })
> db.bookcoll.insert({"name":"java","price":"100","content":"hello java"})
WriteResult({ "nInserted" : 1 })
删除集合所有数据
> db.bookcoll.remove({})
WriteResult({ "nRemoved" : 3 })
~~~
删除集合：`db.collection.drop()`
~~~
> show collections
book
bookcoll
java
system.indexes
> db.book.drop()
true
> show collections
bookcoll
java
system.indexes
~~~
删除数据库：`db.dropDatabase()`
~~~
> show dbs
local  0.078GB
study  0.078GB
> use study
switched to db study
> db.dropDatabase()
{ "dropped" : "study", "ok" : 1 }
> show dbs
local  0.078GB
~~~
# MongoDB使用： 条件操作，排序，索引，分页查询，聚合函数
## mongodb条件查询
    > use study
    switched to db study
    > db.student.insert({"name":"张三","age":18,"score":100,"sex":1})
    WriteResult({ "nInserted" : 1 })
    > db.student.insert({"name":"李四","age":20,"score":90,"sex":1})
    WriteResult({ "nInserted" : 1 })
    > db.student.insert({"name":"小红","age":19,"score":100,"sex":0})
    WriteResult({ "nInserted" : 1 })
    > db.student.insert({"name":"小明","age":19,"score":95,"sex":1})
    WriteResult({ "nInserted" : 1 })
首先要创建一个study数据库，并创建student集合，存入四条数据。  
```shell script
1. 查询年龄大于19的学生。'>'==>'gt' // '>='==>'gte'
> db.student.find({"age":{$gt:19}})
{ "_id" : ObjectId("5ea502960cf4f3a59d7d9850"), "name" : "李四", "age" : 20, "score" : 90, "sex" : 1 }
2. 查询成绩小于等于95的学生 '<'==>'lt' // '<='==>'lte'
> db.student.find({"score":{$lte: 95}})
{ "_id" : ObjectId("5ea502960cf4f3a59d7d9850"), "name" : "李四", "age" : 20, "score" : 90, "sex" : 1 }
{ "_id" : ObjectId("5ea502c70cf4f3a59d7d9852"), "name" : "小明", "age" : 19, "score" : 95, "sex" : 1 }
3. 查询年龄在18-20之间的。 where age>18 and age <20
> db.student.find({"age":{$lt:20,$gt:18}})
{ "_id" : ObjectId("5ea502ae0cf4f3a59d7d9851"), "name" : "小红", "age" : 19, "score" : 100, "sex" : 0 }
{ "_id" : ObjectId("5ea502c70cf4f3a59d7d9852"), "name" : "小明", "age" : 19, "score" : 95, "sex" : 1 }
```
## 分页查询
db.collection.find(query).limit(number)
查询两条数据
```shell script
> db.student.find()
{ "_id" : ObjectId("5ea502830cf4f3a59d7d984f"), "name" : "张三", "age" : 18, "score" : 100, "sex" : 1 }
{ "_id" : ObjectId("5ea502960cf4f3a59d7d9850"), "name" : "李四", "age" : 20, "score" : 90, "sex" : 1 }
{ "_id" : ObjectId("5ea502ae0cf4f3a59d7d9851"), "name" : "小红", "age" : 19, "score" : 100, "sex" : 0 }
{ "_id" : ObjectId("5ea502c70cf4f3a59d7d9852"), "name" : "小明", "age" : 19, "score" : 95, "sex" : 1 }
> db.student.find().limit(2)
{ "_id" : ObjectId("5ea502830cf4f3a59d7d984f"), "name" : "张三", "age" : 18, "score" : 100, "sex" : 1 }
{ "_id" : ObjectId("5ea502960cf4f3a59d7d9850"), "name" : "李四", "age" : 20, "score" : 90, "sex" : 1 }
```
## 跳过skip
查询第2-3条数据
```shell script
> db.student.find().limit(2).skip(1)
{ "_id" : ObjectId("5ea502960cf4f3a59d7d9850"), "name" : "李四", "age" : 20, "score" : 90, "sex" : 1 }
{ "_id" : ObjectId("5ea502ae0cf4f3a59d7d9851"), "name" : "小红", "age" : 19, "score" : 100, "sex" : 0 }
```
通过使用skip和limit我们可以进行分页查询。先在集合中插入10条记录。
```shell script
> db.student.find()
{ "_id" : ObjectId("5ea502830cf4f3a59d7d984f"), "name" : "张三", "age" : 18, "score" : 100, "sex" : 1 }
{ "_id" : ObjectId("5ea502960cf4f3a59d7d9850"), "name" : "李四", "age" : 20, "score" : 90, "sex" : 1 }
{ "_id" : ObjectId("5ea502ae0cf4f3a59d7d9851"), "name" : "小红", "age" : 19, "score" : 100, "sex" : 0 }
{ "_id" : ObjectId("5ea502c70cf4f3a59d7d9852"), "name" : "小明", "age" : 19, "score" : 95, "sex" : 1 }
{ "_id" : ObjectId("5ea508010cf4f3a59d7d9853"), "name" : "王二", "age" : 22, "score" : 88, "sex" : 1 }
{ "_id" : ObjectId("5ea508140cf4f3a59d7d9854"), "name" : "马六", "age" : 21, "score" : 88, "sex" : 0 }
{ "_id" : ObjectId("5ea508280cf4f3a59d7d9855"), "name" : "小芳", "age" : 18, "score" : 88, "sex" : 0 }
{ "_id" : ObjectId("5ea5083a0cf4f3a59d7d9856"), "name" : "小李", "age" : 18, "score" : 93, "sex" : 1 }
{ "_id" : ObjectId("5ea5084a0cf4f3a59d7d9857"), "name" : "小张", "age" : 18, "score" : 98, "sex" : 1 }
{ "_id" : ObjectId("5ea5085d0cf4f3a59d7d9858"), "name" : "小强", "age" : 22, "score" : 70, "sex" : 1 }
```
查询第3页数据，每页3条。 db.collection.find().limit(3).skip((3-1)*3) <==> limit (x-1)*3, 3;
```shell script
> db.student.find().skip(2*3).limit(3)
{ "_id" : ObjectId("5ea508280cf4f3a59d7d9855"), "name" : "小芳", "age" : 18, "score" : 88, "sex" : 0 }
{ "_id" : ObjectId("5ea5083a0cf4f3a59d7d9856"), "name" : "小李", "age" : 18, "score" : 93, "sex" : 1 }
{ "_id" : ObjectId("5ea5084a0cf4f3a59d7d9857"), "name" : "小张", "age" : 18, "score" : 98, "sex" : 1 }
```
## 排序
还是上面的集合，按照年龄升序排序。 db.collection.find(query).sort({key:1}) 1表示升序，-1表示降序
```shell script
> db.student.find().sort({age:1})
{ "_id" : ObjectId("5ea502830cf4f3a59d7d984f"), "name" : "张三", "age" : 18, "score" : 100, "sex" : 1 }
{ "_id" : ObjectId("5ea508280cf4f3a59d7d9855"), "name" : "小芳", "age" : 18, "score" : 88, "sex" : 0 }
{ "_id" : ObjectId("5ea5083a0cf4f3a59d7d9856"), "name" : "小李", "age" : 18, "score" : 93, "sex" : 1 }
{ "_id" : ObjectId("5ea5084a0cf4f3a59d7d9857"), "name" : "小张", "age" : 18, "score" : 98, "sex" : 1 }
{ "_id" : ObjectId("5ea502ae0cf4f3a59d7d9851"), "name" : "小红", "age" : 19, "score" : 100, "sex" : 0 }
{ "_id" : ObjectId("5ea502c70cf4f3a59d7d9852"), "name" : "小明", "age" : 19, "score" : 95, "sex" : 1 }
{ "_id" : ObjectId("5ea502960cf4f3a59d7d9850"), "name" : "李四", "age" : 20, "score" : 90, "sex" : 1 }
{ "_id" : ObjectId("5ea508140cf4f3a59d7d9854"), "name" : "马六", "age" : 21, "score" : 88, "sex" : 0 }
{ "_id" : ObjectId("5ea508010cf4f3a59d7d9853"), "name" : "王二", "age" : 22, "score" : 88, "sex" : 1 }
```
## 索引 createIndex(key,options)
```shell script
> db.student.createIndex({"score":1})
{
        "createdCollectionAutomatically" : false,
        "numIndexesBefore" : 1,
        "numIndexesAfter" : 2,
        "ok" : 1
}
```
{"score":1},表示创建score的索引，升序排序。还可以写复合索引{"score":1,"age":-1}
## 聚合
查询男女生数量，男为1,女为0
```shell script
> db.student.aggregate([{$group:{"_id":"$sex",num_tutorial:{$sum:1}}}])
{ "_id" : 0, "num_tutorial" : 3 }
{ "_id" : 1, "num_tutorial" : 7 }
```


# MongoDB 集群配置
mongodb在生产环境中必然是以集群的形式存在的，不然不安全。mongodb集群主要是一主多从的形式存在。主节点master负责与客户端进行交互，处理读写操作。
从节点slave主要负责从主节点读取数据并保存到本地。
## mongodb主从配置
新建两台服务器，安装mongodb。  
192.168.226.129 master 将192.168.226.129机器作为master节点来配置。  
192.168.226.130 slave  将192.168.226.130机器作为slave从节点配置。  
### 主节点配置
```shell script
[root@localhost data]# mkdir -p /data/master
[root@localhost data]# cd /home/mongodb/bin
[root@localhost bin]# nohup mongod --dbpath /data/master --master &
[3] 3282
[root@localhost bin]# nohup: ignoring input and appending output to `nohup.out'

[root@localhost bin]# cat nohup.out
...
2020-04-27T03:33:42.456-0700 I CONTROL  [initandlisten] allocator: tcmalloc
2020-04-27T03:33:42.456-0700 I CONTROL  [initandlisten] options: { master: true, storage: { dbPath: "/data/master" } }
2020-04-27T03:33:42.459-0700 I JOURNAL  [durability] Durability thread started
2020-04-27T03:33:42.461-0700 I JOURNAL  [journal writer] Journal writer thread started
2020-04-27T03:33:42.463-0700 I NETWORK  [initandlisten] waiting for connections on port 27017
```
### 从节点配置
```shell script
[root@moggledb bin]# mkdir /data/slave
[root@localhost data]# cd /home/mongodb/bin
[root@moggledb bin]# nohup mongod --dbpath /data/slave --slave --source=192.168.226.129:27017 &
[root@moggledb bin]# nohup: ignoring input and appending output to `nohup.out'

[root@moggledb bin]# cat nohup.out
2020-04-30T04:26:14.029-0700 I CONTROL  [initandlisten] allocator: tcmalloc
2020-04-30T04:26:14.029-0700 I CONTROL  [initandlisten] options: { slave: true, source: "192.168.226.129:27017", storage: { dbPath: "/data/slave" } }
2020-04-30T04:26:14.033-0700 I JOURNAL  [journal writer] Journal writer thread started
2020-04-30T04:26:14.035-0700 I NETWORK  [initandlisten] waiting for connections on port 27017
2020-04-30T04:26:15.036-0700 I REPL     [replslave] repl: syncing from host:192.168.226.129:27017
2020-04-30T04:26:20.054-0700 I REPL     [replslave] repl: sleep 1 sec before next pass
2020-04-30T04:26:21.055-0700 I REPL     [replslave] repl: syncing from host:192.168.226.129:27017
```
至此主从节点配置完毕，测试主从节点是否配置成功。  
打开从节点slave 的nohup.out日志.`tail -f nohup.out`
然后在主节点中插入一条数据：
```shell script
> show dbs
local  1.078GB
> use book
switched to db book
> db.coll.insert({"name":"java","price":"100"})
WriteResult({ "nInserted" : 1 })
```
查看从节点日志：
```shell script
Cleanup...
2020-04-30T04:31:47.888-0700 I JOURNAL  [replslave] removeJournalFiles
2020-04-30T04:31:47.889-0700 I JOURNAL  [replslave] journalCleanup...
2020-04-30T04:31:47.889-0700 I JOURNAL  [replslave] removeJournalFiles
2020-04-30T04:31:47.894-0700 I REPL     [replslave] resync: cloning database book to get an initial copy
2020-04-30T04:31:47.912-0700 I INDEX    [replslave] allocating new ns file /data/slave/book.ns, filling with zeroes...
2020-04-30T04:31:47.988-0700 I STORAGE  [FileAllocator] allocating new datafile /data/slave/book.0, filling with zeroes...
2020-04-30T04:31:47.989-0700 I STORAGE  [FileAllocator] done allocating datafile /data/slave/book.0, size: 64MB,  took 0.001 secs
2020-04-30T04:31:47.997-0700 I INDEX    [replslave] build index on: book.coll properties: { v: 1, key: { _id: 1 }, name: "_id_", ns: "book.coll" }
2020-04-30T04:31:47.997-0700 I INDEX    [replslave]      building index using bulk method
2020-04-30T04:31:47.997-0700 I INDEX    [replslave] build index done.  scanned 1 total records. 0 secs
2020-04-30T04:31:47.998-0700 I STORAGE  [replslave] copying indexes for: { name: "coll", options: {} }
```
这样的主从配置如果主节点宕机的话，从节点不会自动转为主节点，会一直等待主节点重启。主节点宕机期间，服务是不可用的。

## mongodb复制(副本集)
mongodb副本集的好处：  
1. 保证数据的安全性  
2. 数据高可用  
3. 灾难恢复  
4. 无需停机维护  
5. 分布式读取数据  
### 副本集的配置
需要准备至少三个节点来测试，本次为了方便使用同一台机器上的不同端口号启动三个节点：  
~~~
192.168.226.130:27017 node1
192.168.226.130:27018 node2
192.168.226.130:27019 node3
~~~
在/data/文件夹下创建三个数据库node1,node2,node3
```shell script
[root@moggledb data]# mkdir node1
[root@moggledb data]# mkdir node2
[root@moggledb data]# mkdir node3
[root@moggledb data]# mkdir log
[root@moggledb data]# ll
total 16
drwxr-xr-x. 2 root root 4096 May  5 20:12 log
drwxr-xr-x. 2 root root 4096 May  5 20:12 node1
drwxr-xr-x. 2 root root 4096 May  5 20:12 node2
drwxr-xr-x. 2 root root 4096 May  5 20:12 node3
```
使用配置文件启动方式，在node1下创建配置文件并复制到另外两个节点
```shell script
[root@moggledb data]# cd node1
[root@moggledb node1]# vi mongodb-node1.conf
dbpath=/data/node1
logpath=/data/log/mongodb-node1.log
logappend=true
fork=true
bind_ip=192.168.226.130
port=27017
replSet=rs0
```
保存退出,复制到另外连个节点中，并修改dbpath,logpath及port
```shell script
[root@moggledb node1]# cp mongodb-node1.conf ../node2/mongodb-node2.conf
[root@moggledb node1]# cp mongodb-node1.conf ../node3/mongodb-node3.conf
[root@moggledb node1]# vi ../node2/mongodb-node2.conf
dbpath=/data/node2
logpath=/data/log/mongodb-node2.log
logappend=true
fork=true
bind_ip=192.168.226.130
port=27018
replSet=rs0
[root@moggledb node2]# vi ../node3/mongodb-node3.conf
dbpath=/data/node3
logpath=/data/log/mongodb-node3.log
logappend=true
fork=true
bind_ip=192.168.226.130
port=27019
replSet=rs0
```
replSet为副本集名称，同一个副本集内的节点，值要一样。  
配置完毕，启动三个节点：
```shell script
[root@moggledb node2]# cd /usr/local/mongodb/bin/
[root@moggledb bin]# ./mongod -f /data/node1/mongodb-node1.conf
about to fork child process, waiting until server is ready for connections.
forked process: 2633
child process started successfully, parent exiting
[root@moggledb bin]# ./mongod -f /data/node2/mongodb-node2.conf
about to fork child process, waiting until server is ready for connections.
forked process: 2649
child process started successfully, parent exiting
[root@moggledb bin]# ./mongod -f /data/node3/mongodb-node3.conf
about to fork child process, waiting until server is ready for connections.
forked process: 2665
child process started successfully, parent exiting
[root@moggledb bin]# ps -ef |grep mongod
root       2633      1  1 20:23 ?        00:00:00 ./mongod -f /data/node1/mongodb-node1.conf
root       2649      1  2 20:24 ?        00:00:00 ./mongod -f /data/node2/mongodb-node2.conf
root       2665      1  3 20:24 ?        00:00:00 ./mongod -f /data/node3/mongodb-node3.conf
root       2680   2581  0 20:24 pts/0    00:00:00 grep mongod
[root@moggledb bin]#
```
三台节点启动成功。下面配置三个节点为副本集：
```shell script
[root@moggledb bin]# ./mongo --host 192.168.226.130 --port 27017
...
2020-05-05T20:23:43.992-0700 I CONTROL  [initandlisten]
> conf = {"_id":"rs0","members":[{"_id":0,"host":"192.168.226.130:27017"},{"_id":1,"host":"192.168.226.130:27018"},{"_id":2,"host":"192.168.226.130:27019"}]}
{
        "_id" : "rs0",
        "members" : [
                {
                        "_id" : 0,
                        "host" : "192.168.226.130:27017"
                },
                {
                        "_id" : 1,
                        "host" : "192.168.226.130:27018"
                },
                {
                        "_id" : 2,
                        "host" : "192.168.226.130:27019"
                }
        ]
}
> rs.initiate(conf)
{ "ok" : 1 }
rs0:OTHER> rs.isMaster()
{
        "setName" : "rs0",
        "setVersion" : 1,
        "ismaster" : true,
        "secondary" : false,
        "hosts" : [
                "192.168.226.130:27017",
                "192.168.226.130:27018",
                "192.168.226.130:27019"
        ],
        "primary" : "192.168.226.130:27017",
        "me" : "192.168.226.130:27017",
        "electionId" : ObjectId("5eb22f9c0520bca5e5033db3"),
        "maxBsonObjectSize" : 16777216,
        "maxMessageSizeBytes" : 48000000,
        "maxWriteBatchSize" : 1000,
        "localTime" : ISODate("2020-05-06T03:32:16.019Z"),
        "maxWireVersion" : 3,
        "minWireVersion" : 0,
        "ok" : 1
}
rs0:PRIMARY> rs.status()
{
        "set" : "rs0",
        "date" : ISODate("2020-05-06T03:33:29.129Z"),
        "myState" : 1,
        "members" : [
                {
                        "_id" : 0,
                        "name" : "192.168.226.130:27017",
                        "health" : 1,
                        "state" : 1,
                        "stateStr" : "PRIMARY",
                        "uptime" : 586,
                        "optime" : Timestamp(1588735898, 1),
                        "optimeDate" : ISODate("2020-05-06T03:31:38Z"),
                        "electionTime" : Timestamp(1588735900, 1),
                        "electionDate" : ISODate("2020-05-06T03:31:40Z"),
                        "configVersion" : 1,
                        "self" : true
                },
                {
                        "_id" : 1,
                        "name" : "192.168.226.130:27018",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 110,
                        "optime" : Timestamp(1588735898, 1),
                        "optimeDate" : ISODate("2020-05-06T03:31:38Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T03:33:29.013Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T03:33:29.078Z"),
                        "pingMs" : 0,
                        "configVersion" : 1
                },
                {
                        "_id" : 2,
                        "name" : "192.168.226.130:27019",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 110,
                        "optime" : Timestamp(1588735898, 1),
                        "optimeDate" : ISODate("2020-05-06T03:31:38Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T03:33:29.013Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T03:33:29.078Z"),
                        "pingMs" : 0,
                        "configVersion" : 1
                }
        ],
        "ok" : 1
}
```
当前节点为主节点PRIMARY,上面可以看出节点的详细信息。
副本集的主节点负责与客户端交互，进行读和写。从节点复制主机点数据，监听主节点 的心跳。
#### 副本集主从复制测试。
在主节点插入数据
```shell script
rs0:PRIMARY> show dbs
local  1.078GB
rs0:PRIMARY> use test
switched to db test
rs0:PRIMARY> db.coll.insert({"name":"zhangsan"})
WriteResult({ "nInserted" : 1 })
rs0:PRIMARY> db.coll.insert({"name":"lisi"})
WriteResult({ "nInserted" : 1 })
rs0:PRIMARY> db.coll.find()
{ "_id" : ObjectId("5eb230a80a6d7da784ff2c83"), "name" : "zhangsan" }
{ "_id" : ObjectId("5eb230ad0a6d7da784ff2c84"), "name" : "lisi" }
```
连接从节点：  
```shell script
[root@moggledb bin]# ./mongo --host 192.168.226.130 --port 27018
...
2020-05-05T20:24:21.584-0700 I CONTROL  [initandlisten]
rs0:SECONDARY> rs.slaveOk()
rs0:SECONDARY> show dbs
local  1.078GB
test   0.078GB
rs0:SECONDARY> use test
switched to db test
rs0:SECONDARY> db.coll.find()
{ "_id" : ObjectId("5eb230a80a6d7da784ff2c83"), "name" : "zhangsan" }
{ "_id" : ObjectId("5eb230ad0a6d7da784ff2c84"), "name" : "lisi" }
rs0:SECONDARY> rs.status()
{
        "set" : "rs0",
        "date" : ISODate("2020-05-06T03:45:56.163Z"),
        "myState" : 2,
        "syncingTo" : "192.168.226.130:27017",
        "members" : [
                {
                        "_id" : 0,
                        "name" : "192.168.226.130:27017",
                        "health" : 1,
                        "state" : 1,
                        "stateStr" : "PRIMARY",
                        "uptime" : 857,
                        "optime" : Timestamp(1588736173, 1),
                        "optimeDate" : ISODate("2020-05-06T03:36:13Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T03:45:54.692Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T03:45:54.665Z"),
                        "pingMs" : 0,
                        "electionTime" : Timestamp(1588735900, 1),
                        "electionDate" : ISODate("2020-05-06T03:31:40Z"),
                        "configVersion" : 1
                },
                {
                        "_id" : 1,
                        "name" : "192.168.226.130:27018",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 1295,
                        "optime" : Timestamp(1588736173, 1),
                        "optimeDate" : ISODate("2020-05-06T03:36:13Z"),
                        "syncingTo" : "192.168.226.130:27017",
                        "configVersion" : 1,
                        "self" : true
                },
                {
                        "_id" : 2,
                        "name" : "192.168.226.130:27019",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 857,
                        "optime" : Timestamp(1588736173, 1),
                        "optimeDate" : ISODate("2020-05-06T03:36:13Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T03:45:54.677Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T03:45:54.677Z"),
                        "pingMs" : 0,
                        "syncingTo" : "192.168.226.130:27017",
                        "configVersion" : 1
                }
        ],
        "ok" : 1
}
rs0:SECONDARY>
```
可以看出主节点插入数据，在从节点中也有数据,从节点只能读数据，不能写数据，主节点可以读写数据。
#### 故障转移
模拟主节点宕机，关闭主节点服务：
```shell script
[root@moggledb bin]# ./mongod --shutdown --dbpath=/data/node1
killing process with pid: 2633
[root@moggledb bin]#
```
再次查看从节点状态：  
```shell script
[root@moggledb bin]# mongo --host 192.168.226.130 --port 27018
...
2020-05-05T20:24:21.584-0700 I CONTROL  [initandlisten]
rs0:PRIMARY> rs.status()
{
        "set" : "rs0",
        "date" : ISODate("2020-05-06T03:52:08.238Z"),
        "myState" : 1,
        "members" : [
                {
                        "_id" : 0,
                        "name" : "192.168.226.130:27017",
                        "health" : 0,
                        "state" : 8,
                        "stateStr" : "(not reachable/healthy)",
                        "uptime" : 0,
                        "optime" : Timestamp(0, 0),
                        "optimeDate" : ISODate("1970-01-01T00:00:00Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T03:52:07.354Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T03:50:23.111Z"),
                        "pingMs" : 0,
                        "lastHeartbeatMessage" : "Failed attempt to connect to 192.168.226.130:27017; couldn't connect to server 192.168.226.130:27017 (192.168.226.130), connection attem
pt failed",
                        "configVersion" : -1
                },
                {
                        "_id" : 1,
                        "name" : "192.168.226.130:27018",
                        "health" : 1,
                        "state" : 1,
                        "stateStr" : "PRIMARY",
                        "uptime" : 1667,
                        "optime" : Timestamp(1588736173, 1),
                        "optimeDate" : ISODate("2020-05-06T03:36:13Z"),
                        "electionTime" : Timestamp(1588737026, 1),
                        "electionDate" : ISODate("2020-05-06T03:50:26Z"),
                        "configVersion" : 1,
                        "self" : true
                },
                {
                        "_id" : 2,
                        "name" : "192.168.226.130:27019",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 1229,
                        "optime" : Timestamp(1588736173, 1),
                        "optimeDate" : ISODate("2020-05-06T03:36:13Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T03:52:07.216Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T03:52:07.197Z"),
                        "pingMs" : 0,
                        "configVersion" : 1
                }
        ],
        "ok" : 1
}
```
从上面状态信息可以看出，27017节点无心跳，27018节点变为主节点：`{"stateStr":"PRIMARY"}`  
如果此时重启27017节点：
```shell script
[root@moggledb bin]# ./mongod -f /data/node1/mongodb-node1.conf
rs0:SECONDARY> rs.status()
{
        "set" : "rs0",
        "date" : ISODate("2020-05-06T03:54:46.892Z"),
        "myState" : 1,
        "members" : [
                {
                        "_id" : 0,
                        "name" : "192.168.226.130:27017",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 17,
                        "optime" : Timestamp(1588736173, 1),
                        "optimeDate" : ISODate("2020-05-06T03:36:13Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T03:54:45.678Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T03:54:45.725Z"),
                        "pingMs" : 0,
                        "configVersion" : 1
                },
                {
                        "_id" : 1,
                        "name" : "192.168.226.130:27018",
                        "health" : 1,
                        "state" : 1,
                        "stateStr" : "PRIMARY",
                        "uptime" : 1825,
                        "optime" : Timestamp(1588736173, 1),
                        "optimeDate" : ISODate("2020-05-06T03:36:13Z"),
                        "electionTime" : Timestamp(1588737026, 1),
                        "electionDate" : ISODate("2020-05-06T03:50:26Z"),
                        "configVersion" : 1,
                        "self" : true
                },
                {
                        "_id" : 2,
                        "name" : "192.168.226.130:27019",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 1387,
                        "optime" : Timestamp(1588736173, 1),
                        "optimeDate" : ISODate("2020-05-06T03:36:13Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T03:54:45.517Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T03:54:45.517Z"),
                        "pingMs" : 0,
                        "configVersion" : 1
                }
        ],
        "ok" : 1
}
```
27017节点仍为从节点，27018为主节点。可以为节点配置优先级，这样主节点重启后仍是主节点。
**只有主节点才可以设置副本集配置**
所以先进入主节点的shell命令行：
```shell script
rs0:PRIMARY> rs.conf()
{
        "_id" : "rs0",
        "version" : 1,
        "members" : [
                {
                        "_id" : 0,
                        "host" : "192.168.226.130:27017",
                        "arbiterOnly" : false,
                        "buildIndexes" : true,
                        "hidden" : false,
                        "priority" : 1,
                        "tags" : {

                        },
                        "slaveDelay" : 0,
                        "votes" : 1
                },
                {
                        "_id" : 1,
                        "host" : "192.168.226.130:27018",
                        "arbiterOnly" : false,
                        "buildIndexes" : true,
                        "hidden" : false,
                        "priority" : 1,
                        "tags" : {

                        },
                        "slaveDelay" : 0,
                        "votes" : 1
                },
                {
                        "_id" : 2,
                        "host" : "192.168.226.130:27019",
                        "arbiterOnly" : false,
                        "buildIndexes" : true,
                        "hidden" : false,
                        "priority" : 1,
                        "tags" : {

                        },
                        "slaveDelay" : 0,
                        "votes" : 1
                }
        ],
        "settings" : {
                "chainingAllowed" : true,
                "heartbeatTimeoutSecs" : 10,
                "getLastErrorModes" : {

                },
                "getLastErrorDefaults" : {
                        "w" : 1,
                        "wtimeout" : 0
                }
        }
}
```
将27017优先级设为三个节点的最高值：
```shell script
rs0:PRIMARY> var conf=rs.conf()
rs0:PRIMARY> conf.members[0].priority=5
5
rs0:PRIMARY> rs.reconfig(conf)
```
然后重新启动27017节点
```shell script
[root@moggledb bin]# ./mongod --shutdown --dbpath=/data/node1
killing process with pid: 4252
[root@moggledb bin]# ./mongod -f /data/node1/mongodb-node1.conf
about to fork child process, waiting until server is ready for connections.
forked process: 5614
child process started successfully, parent exiting
[root@moggledb bin]# ./mongo --host 192.168.226.130 --port 27017
...
rs0:SECONDARY> rs.isMaster()
rs0:SECONDARY> rs.isMaster()
{
        "setName" : "rs0",
        "setVersion" : 2,
        "ismaster" : true,
        "secondary" : false,
        "hosts" : [
                "192.168.226.130:27017",
                "192.168.226.130:27018",
                "192.168.226.130:27019"
        ],
        "primary" : "192.168.226.130:27017",
        "me" : "192.168.226.130:27017",
        "electionId" : ObjectId("5eb23de4b1aea8240124474b"),
        "maxBsonObjectSize" : 16777216,
        "maxMessageSizeBytes" : 48000000,
        "maxWriteBatchSize" : 1000,
        "localTime" : ISODate("2020-05-06T04:32:49.878Z"),
        "maxWireVersion" : 3,
        "minWireVersion" : 0,
        "ok" : 1
}
rs0:PRIMARY> rs.status()
{
        "set" : "rs0",
        "date" : ISODate("2020-05-06T04:33:55.150Z"),
        "myState" : 1,
        "members" : [
                {
                        "_id" : 0,
                        "name" : "192.168.226.130:27017",
                        "health" : 1,
                        "state" : 1,
                        "stateStr" : "PRIMARY",
                        "uptime" : 106,
                        "optime" : Timestamp(1588739463, 1),
                        "optimeDate" : ISODate("2020-05-06T04:31:03Z"),
                        "electionTime" : Timestamp(1588739556, 1),
                        "electionDate" : ISODate("2020-05-06T04:32:36Z"),
                        "configVersion" : 2,
                        "self" : true
                },
                {
                        "_id" : 1,
                        "name" : "192.168.226.130:27018",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 105,
                        "optime" : Timestamp(1588739463, 1),
                        "optimeDate" : ISODate("2020-05-06T04:31:03Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T04:33:53.661Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T04:33:54.069Z"),
                        "pingMs" : 23,
                        "configVersion" : 2
                },
                {
                        "_id" : 2,
                        "name" : "192.168.226.130:27019",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 105,
                        "optime" : Timestamp(1588739463, 1),
                        "optimeDate" : ISODate("2020-05-06T04:31:03Z"),
                        "lastHeartbeat" : ISODate("2020-05-06T04:33:53.661Z"),
                        "lastHeartbeatRecv" : ISODate("2020-05-06T04:33:55.124Z"),
                        "pingMs" : 23,
                        "configVersion" : 2
                }
        ],
        "ok" : 1
}
```
可以看出27017重新成为主节点
## mongodb 分片技术
在mongodb中存在另一种集群技术--分片技术。用于解决mongodb数量大的需求。
当mongodb中存在海量数据时，一台服务器可能不足以存储数据，也肯能不足以提供可接受的吞吐量。此时就可以在多台数据上分割数据，使得数据库能存放更多的数据。
### 为什么使用分片技术？
* 在mongodb副本集中，所有的写入操作都在主节点。客户端只与主节点打交道，那么如果数据量非常大/访问频率非常高的话，主节点的压力就会非常大。
* 副本集的节点数量限制。  
    **官方文档给出：mongodb单个副本集下所有节点不能超过50个，投票节点不能超过7个**
* 当请求量巨大时，可能会出现内存不足。   
    使用NoSql数据库的一个重要原因就是内存数据库，效率高，但是数据量过大时就会出现内存不足的情况。
* 本地磁盘不足。
    mongodb的数据也会持久化在本地硬盘上，如果数据量非常大，就会出现本地磁盘不足的情况
* 垂直拓展价格昂贵
    垂直拓展：增加更多的cpu和存储资源来扩展容量  
    
高数据量和高吞吐量回对单机造成很大的压力，解决办法：  
    水平拓展：将数据集分布在多个服务器上，即水平切片。
    垂直拓展。
    
### 分片技术的组件
mongodb的分片设计主要包含三个组件，
![摘自菜鸟教程](https://www.runoob.com/wp-content/uploads/2013/12/sharding.png)
    
    shard: 用于存储实际的数据块。实际生产环境中一个shard server角色可以由多个节点组成一个副本集承担，防止节点故障。
    config server: 所有存取数据的方式，所有shard节点的信息，分片功能的配置信息。config sever是由一组至少三个的Mongodbs实例组成的集群。
    query Routers: 前端路由，客户端由此接入。路由没有任何数据，请求过来后会直接找config server，由config server决定去哪儿个分片取数据，以及怎么取数据。
        
由上面图片可以大概分析请求的流程：  
    客户端请求到router, 然后router调用config server获取到数据的位置, 然后router会直接找到存放数据的shard来存取数据。
### 分片技术的实现 
受电脑配置限制，我启动三台服务器： `192.168.226.130, 192.168.226.131, 192.168.226.132`分别安装mongodb。  
节点分布如下：
~~~
shard1: 192.168.226.131:27017
shard2: 192.168.226.131:27018
shard3: 192.168.226.131:27019

shard4: 192.168.226.132:27017
shard5: 192.168.226.132:27018
shard6: 192.168.226.132:27019

config server:192.168.226.130:27017

~~~
### shard节点启动
分别在节点上创建数据库文件夹：  
![](https://img2020.cnblogs.com/blog/1602537/202005/1602537-20200508174452464-577727772.png)
![](https://img2020.cnblogs.com/blog/1602537/202005/1602537-20200508174508003-646167306.png)  
在s1下创建文件：mongodb-shard.conf 并编辑如下内容：
```properties
dbpath=/data/s1
logpath=/data/log/mongodb-s1.log
logappend=true
fork=true
bind_ip=192.168.226.131
port=27017
```
修改信息复制到其他的文件夹下。
分别启动各个shard节点
```shell script
[root@mongodb2 data]# mongod -f /data/s1/mongodb-shard.conf
......
[root@mongodb3 data]# mongod -f /data/s6/mongodb-shard.conf
```
查看节点启动状态：  
![](https://img2020.cnblogs.com/blog/1602537/202005/1602537-20200508180409198-533974424.png)
![](https://img2020.cnblogs.com/blog/1602537/202005/1602537-20200508180446237-1488051152.png)
### config节点启动
在192.168.226.130 节点下创建/data/config 数据库，并添加配置文件：mongodb-config.conf
```properties
dbpath=/data/config
logpath=/data/log/mongodb-config.log
logappend=true
fork=true
bind_ip=192.168.226.130
port=27017
```
创建mongos文件，添加mongodb-mongos.conf文件，文件内容：
```properties
port=27018
bind_ip=192.168.226.130
logpath=/data/log/mongodb-route.log
logappend=true
chunkSize=500
configdb=192.168.226.130:27017
fork=true
```
启动路由： `mongos -f /data/mongos/mongodb-mongos.conf`  
查看启动状态：
```shell script
[root@moggledb mongos]# ps -ef |grep mongo
root      15086      1  0 19:49 ?        00:00:02 mongod -f /data/config/mongodb-config.conf
root      15124      1  0 19:53 ?        00:00:00 mongos -f /data/mongos/mongodb-mongos.conf
root      15145  15062  0 19:54 pts/0    00:00:00 grep mongo
[root@moggledb mongos]#
```
使用mongo命令进入路由： `mongo --host 192.168.226.130 --port 27018 admin`  
```shell script
## 添加shard节点：
mongos> db.runCommand({addshard:"192.168.226.131:27017"})
{ "shardAdded" : "shard0000", "ok" : 1 }
## 添加其他shard节点......
mongos> db.runCommand({addshard:"192.168.226.132:27019"})
{ "shardAdded" : "shard0005", "ok" : 1 }
## 设置分片存储数据库
mongos> db.runCommand({enablesharding:"study"})
{ "ok" : 1 }
## 指定数据库中的表通过什么分片, 我指定了对study数据库下的book表进行分片，根据表字段id和time分片, 指定的key在文档中必须存在，否则添加失败
mongos> db.runCommand({shardcollection:"study.book", key:{id:1, time:1}})
{ "collectionsharded" : "study.book", "ok" : 1 }
```
[更多关于mongodb片建信息](https://www.jianshu.com/p/bb5f18a852c0)

查看分片状态：
```shell script
mongos> db.runCommand({listshards:1})
{
        "shards" : [
                {
                        "_id" : "shard0000",
                        "host" : "192.168.226.131:27017"
                },
                {
                        "_id" : "shard0001",
                        "host" : "192.168.226.131:27018"
                },
                {
                        "_id" : "shard0002",
                        "host" : "192.168.226.131:27019"
                },
                {
                        "_id" : "shard0003",
                        "host" : "192.168.226.132:27017"
                },
                {
                        "_id" : "shard0004",
                        "host" : "192.168.226.132:27018"
                },
                {
                        "_id" : "shard0005",
                        "host" : "192.168.226.132:27019"
                }
        ],
        "ok" : 1
}
```
插入三条数据：
```shell script
mongos> db.book.insert({"id":"1","name":"java","content":"hello java!","time":"2020-05-05"})
WriteResult({ "nInserted" : 1 })
mongos> db.book.insert({"id":"2","name":"php","content":"hello java!","time":"2020-05-06"})
WriteResult({ "nInserted" : 1 })
mongos> db.book.insert({"id":"3","name":"python","content":"hello java!","time":"2020-05-07"})
WriteResult({ "nInserted" : 1 })
mongos> db.book.find()
{ "_id" : ObjectId("5eb622cb3fad9d07777f6ca0"), "id" : "1", "name" : "java", "content" : "hello java!", "time" : "2020-05-05" }
{ "_id" : ObjectId("5eb622e03fad9d07777f6ca1"), "id" : "2", "name" : "php", "content" : "hello java!", "time" : "2020-05-06" }
{ "_id" : ObjectId("5eb622ee3fad9d07777f6ca2"), "id" : "3", "name" : "python", "content" : "hello java!", "time" : "2020-05-07" }
```
可以看到在路由段就可以查到数据，因此在客户端只需要连接路由的地址，就可以查询整个集群数据，务需关心数据具体存在哪儿。  
下面我们看一下数据实际存储的数据块：  
 首先登录进config库：
 ```shell script
[root@moggledb ~]# mongo --host 192.168.226.130 --port 27017 study
......
2020-05-08T19:49:05.408-0700 I CONTROL  [initandlisten] **        We suggest setting it to 'never'
2020-05-08T19:49:05.408-0700 I CONTROL  [initandlisten]
> db.book.find()
> show dbs
config  0.078GB
local   0.078GB
```
可以看到根本没有study数据库, 之前我们说数据实际存储节点在shard节点中，config存储的是数据的位置。 下面验证一下：
```shell script
> use config
switched to db config
> show collections
actionlog
changelog
chunks
collections
databases
lockpings
locks
mongos
settings
shards
system.indexes
tags
version
```
上面是config库的表数据，我们可以看到chunks表存放数据块信息，shards存放shard节点信息
```shell script
> db.shards.find()
{ "_id" : "shard0000", "host" : "192.168.226.131:27017" }
{ "_id" : "shard0001", "host" : "192.168.226.131:27018" }
{ "_id" : "shard0002", "host" : "192.168.226.131:27019" }
{ "_id" : "shard0003", "host" : "192.168.226.132:27017" }
{ "_id" : "shard0004", "host" : "192.168.226.132:27018" }
{ "_id" : "shard0005", "host" : "192.168.226.132:27019" }
> db.chunks.find()
{ "_id" : "study.book-id_MinKeytime_MinKey", "lastmod" : Timestamp(1, 0), "lastmodEpoch" : ObjectId("5eb62121eebc265b95c4a76d"), "ns" : "study.book", "min" : { "id" : { "$minKey" : 1 },
"time" : { "$minKey" : 1 } }, "max" : { "id" : { "$maxKey" : 1 }, "time" : { "$maxKey" : 1 } }, "shard" : "shard0000" }
```
其他表数据信息不一一测试，有兴趣自己验证。  
在上面我们看到数据都存在shard0000节点上，根据节点表信息可以知道shard0000节点地址为 `192.168.226.131:27017` 登录上去进行验证。
```shell script
[root@mongodb2 ~]# mongo --host 192.168.226.131 --port 27017 study
.....
2020-05-08T11:00:16.019-0700 I CONTROL  [initandlisten]
> show dbs
local  0.078GB
study  0.078GB
> use study
switched to db study
> db.book.find()
{ "_id" : ObjectId("5eb622cb3fad9d07777f6ca0"), "id" : "1", "name" : "java", "content" : "hello java!", "time" : "2020-05-05" }
{ "_id" : ObjectId("5eb622e03fad9d07777f6ca1"), "id" : "2", "name" : "php", "content" : "hello java!", "time" : "2020-05-06" }
{ "_id" : ObjectId("5eb622ee3fad9d07777f6ca2"), "id" : "3", "name" : "python", "content" : "hello java!", "time" : "2020-05-07" }
```
可以在切换其他几个节点查看是否有数据：
```shell script
[root@mongodb2 ~]# mongo --host 192.168.226.131 --port 27018 study
.....
> show dbs
local  0.078GB
```
可以看到因为没有数据，所以study数据库还没有初始化。





  
   
### 分片与副本集的区别？
在上一篇我们学习了集群配置--副本集的配置, 副本集是集群配置的方式, 分片也是集群配置的方式, 那么这两种方式的区别是什么?
# MongoDB java操做mongodb
```java
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
@SpringBootTest
class MongodbApplicationTests {

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
}
```
# Mongodb 用户和身份验证
mongodb默认是没有用户名和密码的，只要连接成功就可以操作，但是如果要将mongodb作为项目中间件上线的话，肯定不希望任何人都可以访问，为了保证数据安全，mongodb提供了addUser方法来添加用户。
该方法包含三个参数：
~~~
user: 用户名   
pwd: 密码  
roles: 集合类型。用户所拥有的角色
添加用户：db.createUser({user:"用户名", pwd:"密码", roles:[{role:"角色", db:"数据库"}]})  
删除用户：db.dropUser("用户名")
~~~
## Mongodb用户分为两类：超级用户和数据库用户
数据库的用户都存放在admin数据库下：
```shell script
db.system.users
```
### 超级用户
mongodb的超级用户存储在admin数据库中,该数据库中的用户可以对所有的数据库进行任意操作，拥有最大的权限。mongodb刚安装时，admin数据库是空的。
### 数据库用户
数据库用户是存放在单个数据库中的，只能访问对应的数据库。
### 用户和权限的特性
* 数据库是由超级用户创建的，一个数据库可以包含多个用户，一个用户只能在一个数据库下。不同的数据库下用户名可以相同
* 如果在admin数据库中不存在用户，即使在mongodb启动时添加了-auth参数，此时不进行任何认证还是可以作任何操作
* 在admin数据库创建的用户具有超级权限，可以对系统内的任何数据库数据对象进行操作
* 数据库test下的用户test_user1不能访问其他数据库如local、test2，但是可以访问同一数据库下其他用户如test_user2创建的数据
* 不同数据库下的同一用户名用户，在一个数据库登陆后，不能再另一个数据库登录。如test1、test2数据库下都有用户test_user用户，那么test_user用户再test1登录后，不能再test2登录。
### 验证以上特性
打开之前配置的mongodb集群，连入master：27017端口。
```shell script
[root@moggledb ~]# mongo --host 192.168.226.130 --port 27017
...
rs0:PRIMARY>
```
此时admin数据库中仍没有任何用户，因此可以再任意数据库下访问其他数据库。
```shell script
rs0:PRIMARY> show dbs
local  1.078GB
test   0.078GB
rs0:PRIMARY> use local
switched to db local
rs0:PRIMARY> use test
switched to db test
```
从local数据库下访问test数据库，切换成功。  
查看admin数据库下的users表。  
```shell script
rs0:PRIMARY> db.system.users.find()
rs0:PRIMARY> 
```
此时db.system.users为空，因此是超级管理员权限，可以访问任意数据库。
+ 验证：数据库是由超级用户创建的，一个数据库可以包含多个用户，一个用户只能在一个数据库下。不同的数据库下用户名可以相同

```shell script
rs0:PRIMARY> use test1
rs0:PRIMARY> db.createUser({user:"admin", pwd:"123465", roles:[{role:"readWrite", db:"test1"}]})
Successfully added user: {
        "user" : "admin",
        "roles" : [
                {
                        "role" : "readWrite",
                        "db" : "test1"
                }
        ]
}
```
用户创建成功后，用户信息会存放在admin数据库下的system.users中。再admin下再创建一个超级用户
```shell script
rs0:PRIMARY> use admin
switched to db admin
rs0:PRIMARY> db.system.users.find()
{ "_id" : "test1.admin", "user" : "admin", "db" : "test1", "credentials" : { "SCRAM-SHA-1" : { "iterationCount" : 10000, "salt" : "2gOLsZlScfAloQ9rdiL3tA==", "storedKey" : "JWG+rgHy+jbXG
1EDxYDXgfd17Jw=", "serverKey" : "cWCH2UwJ3f4Tg86smNZtenC0EUs=" } }, "roles" : [ { "role" : "readWrite", "db" : "test1" } ] }
rs0:PRIMARY> db.createUser({user:"root", pwd:"root", roles:[{role:"userAdminAnyDatabase", db:"admin"}]})
Successfully added user: {
        "user" : "root",
        "roles" : [
                {
                        "role" : "userAdminAnyDatabase",
                        "db" : "admin"
                }
        ]
}
rs0:PRIMARY> db.system.users.find()
{ "_id" : "test1.admin", "user" : "admin", "db" : "test1", "credentials" : { "SCRAM-SHA-1" : { "iterationCount" : 10000, "salt" : "2gOLsZlScfAloQ9rdiL3tA==", "storedKey" : "JWG+rgHy+jbXG
1EDxYDXgfd17Jw=", "serverKey" : "cWCH2UwJ3f4Tg86smNZtenC0EUs=" } }, "roles" : [ { "role" : "readWrite", "db" : "test1" } ] }
{ "_id" : "admin.root", "user
" : "root", "db" : "admin", "credentials" : { "SCRAM-SHA-1" : { "iterationCount" : 10000, "salt" : "PJPZSCq9YqY96QOWgVPnRQ==", "storedKey" : "VAO+Now5XIzkmew
kRFr3TIKU6J0=", "serverKey" : "9V2ArT9CWsunH8TCk/lY1oCAUzk=" } }, "roles" : [ { "role" : "userAdminAnyDatabase", "db" : "admin" } ] }
```
此时我们test1数据库有了用户admin,然后再test2下创建相同用户admin
```shell script
rs0:PRIMARY> use test2
switched to db test2
rs0:PRIMARY> db.createUser({user:"admin", pwd:"123456", roles:[{role:"readWrite", db:"test2"}]})
Successfully added user: {
        "user" : "admin",
        "roles" : [
                {
                        "role" : "readWrite",
                        "db" : "test2"
                }
        ]
}
rs0:PRIMARY> db.createUser({user:"test2",pwd:"test2",roles:[{role:"read", db:"test2"}]})
Successfully added user: {
        "user" : "test2",
        "roles" : [
                {
                        "role" : "read",
                        "db" : "test2"
                }
        ]
}
rs0:PRIMARY> db.getUsers()
[
        {
                "_id" : "test2.admin",
                "user" : "admin",
                "db" : "test2",
                "roles" : [
                        {
                                "role" : "readWrite",
                                "db" : "test2"
                        }
                ]
        },
        {
                "_id" : "test2.test2",
                "user" : "test2",
                "db" : "test2",
                "roles" : [
                        {
                                "role" : "read",
                                "db" : "test2"
                        }
                ]
        }
]
rs0:PRIMARY>
```
可以看到，不同数据库下可以有相同的用户名。同一个数据库下可以有多个用户。
* 在admin数据库创建的用户具有超级权限，可以对系统内的任何数据库数据对象进行操作 
 
退出primary节点，停掉集群节点。先停掉备份节点，在停主节点。
```shell script
rs0:PRIMARY> exit
bye
[root@moggledb ~]# mongod --shutdown --dbpath /data/node3
killing process with pid: 2768
[root@moggledb ~]# mongod --shutdown --dbpath /data/node2
killing process with pid: 2679
[root@moggledb ~]# mongod --shutdown --dbpath /data/node1
killing process with pid: 2595
[root@moggledb ~]# ps -ef |grep mongod
root       8347   2571  0 22:10 pts/0    00:00:00 grep mongod
[root@moggledb ~]#
```
在primary节点上生成keyfile文件
```shell script
[root@moggledb ~]# openssl rand -base64 666 > /data/node1/keyfile
[root@moggledb ~]# chmod 600 /data/node1/keyfile
```
将keyfile复制到其他节点上，并修改权限为600
```shell script
[root@moggledb ~]# openssl rand -base64 666 > /data/node1/keyfile
[root@moggledb ~]# chmod 600 /data/node1/keyfile
[root@moggledb ~]# cp /data/node1/keyfile /data/node2/
[root@moggledb ~]# cp /data/node1/keyfile /data/node3/
[root@moggledb ~]# chmod 600 /data/node2/keyfile
[root@moggledb ~]# chmod 600 /data/node3/keyfile
```
修改primary节点启动配置文件：
```properties
auth=true
oplogSize=100
keyFile=/data/node1/keyfile
```
修改secondary节点配置文件：
```properties
oplogSize=100
keyFile=/data/node2/keyfile
```
启动副本集，先启动主节点：
```shell script
[root@moggledb ~]# mongod -f /data/node1/mongodb-node1.conf
about to fork child process, waiting until server is ready for connections.
forked process: 8368
child process started successfully, parent exiting
[root@moggledb ~]# mongod -f /data/node2/mongodb-node2.conf
about to fork child process, waiting until server is ready for connections.
forked process: 8452
child process started successfully, parent exiting
[root@moggledb ~]# mongod -f /data/node3/mongodb-node3.conf
about to fork child process, waiting until server is ready for connections.
forked process: 8535
child process started successfully, parent exiting
[root@moggledb ~]# ps -ef |grep mongod
root       8368      1  1 22:22 ?        00:00:00 mongod -f /data/node1/mongodb-node1.conf
root       8452      1  1 22:22 ?        00:00:00 mongod -f /data/node2/mongodb-node2.conf
root       8535      1  2 22:22 ?        00:00:00 mongod -f /data/node3/mongodb-node3.conf
root       8598   2571  0 22:23 pts/0    00:00:00 grep mongod
[root@moggledb ~]#
```
连接主节点
```shell script
[root@moggledb node1]# mongo --username root --password root admin --host 192.168.226.130 --port 27017
MongoDB shell version: 3.0.6
connecting to: 192.168.226.130:27017/admin
rs0:PRIMARY> show dbs
admin  0.078GB
local  1.078GB
test   0.078GB
test1  0.078GB
rs0:PRIMARY>
```
root用户是超级用户，可以查看所有的数据库。但是如果要操作某一个数据库内的数据的话，还是需要使用数据的用户，root用户无权限。
```shell script
rs0:PRIMARY> db.system.users.find()
{ "_id" : "test1.admin", "user" : "admin", "db" : "test1", "credentials" : { "SCRAM-SHA-1" : { "iterationCount" : 10000, "salt" : "2gOLsZlScfAloQ9rdiL3tA==", "storedKey" : "JWG+rgHy+jbXG1EDxYDXgfd17Jw=", "serverKey" : "cWCH2UwJ3f4Tg86smNZtenC0EUs=" } }, "roles" : [ { "role" : "readWrite", "db" : "test1" } ] }
{ "_id" : "admin.root", "user" : "root", "db" : "admin", "credentials" : { "SCRAM-SHA-1" : { "iterationCount" : 10000, "salt" : "PJPZSCq9YqY96QOWgVPnRQ==", "storedKey" : "VAO+Now5XIzkmewkRFr3TIKU6J0=", "serverKey" : "9V2ArT9CWsunH8TCk/lY1oCAUzk=" } }, "roles" : [ { "role" : "userAdminAnyDatabase", "db" : "admin" } ] }
{ "_id" : "test2.admin", "user" : "admin", "db" : "test2", "credentials" : { "SCRAM-SHA-1" : { "iterationCount" : 10000, "salt" : "bpf9kX28M/qcjItzYRCh0A==", "storedKey" : "ptn1v5z70h0u2HYdh2SgBt8lI+s=", "serverKey" : "Wh3m8g2olZhoP5Xho2p6HS+CJrs=" } }, "roles" : [ { "role" : "readWrite", "db" : "test2" } ] }
{ "_id" : "test2.test2", "user" : "test2", "db" : "test2", "credentials" : { "SCRAM-SHA-1" : { "iterationCount" : 10000, "salt" : "8XmR1dpRQc4Tpb0JLzEsZw==", "storedKey" : "npUCRpSPlI5xAmEkbeKkJU5BC9U=", "serverKey" : "ZzbabAQR5VblYw+Lx4yE82Y33mQ=" } }, "roles" : [ { "role" : "read", "db" : "test2" } ] }
rs0:PRIMARY> use test1
switched to db test1
rs0:PRIMARY> show collections
2020-05-06T23:55:18.738-0700 E QUERY    Error: listCollections failed: {
        "ok" : 0,
        "errmsg" : "not authorized on test1 to execute command { listCollections: 1.0 }",
        "code" : 13
}
    at Error (<anonymous>)
    at DB._getCollectionInfosCommand (src/mongo/shell/db.js:646:15)
    at DB.getCollectionInfos (src/mongo/shell/db.js:658:20)
    at DB.getCollectionNames (src/mongo/shell/db.js:669:17)
    at shellHelper.show (src/mongo/shell/utils.js:625:12)
    at shellHelper (src/mongo/shell/utils.js:524:36)
    at (shellhelp2):1:1 at src/mongo/shell/db.js:646
rs0:PRIMARY>
```
使用test1数据库的admin用户认证：
```shell script
rs0:PRIMARY> db.auth("admin","123465")
1
rs0:PRIMARY> show collections
coll
system.indexes
```
注意我在上面设置test1数据库密码时，写错了，写成了123465，因此这里认证时密码也要是123465。
* 使用test2数据库的admin用户插入一条数据，用test2用户去查看数据
```shell script
rs0:PRIMARY> use test2
switched to db test2
rs0:PRIMARY> db.auth("admin","123456")
rs0:PRIMARY> db.coll.insert({"name":"java"})
WriteResult({ "nInserted" : 1 })
rs0:PRIMARY> db.auth("test2","test2")
1
rs0:PRIMARY> db.coll.find()
{ "_id" : ObjectId("5eb3b5bd7e5f031e229d4cf2"), "name" : "java" }
rs0:PRIMARY> db.coll.insert({"text":"hello mongodb"})
WriteResult({
        "writeError" : {
                "code" : 13,
                "errmsg" : "not authorized on test2 to execute command { insert: \"coll\", documents: [ { _id: ObjectId('5eb3b63a7e5f031e229d4cf3'), text: \"hello mongodb\" } ], ordered:
 true }"
        }
})
rs0:PRIMARY>
```
可以看到切换为test2用户后，test2用户可以查看admin用户插入的数据，但是test2用户只有读的权限，因此插入数据失败。
[更多角色权限说明](https://blog.csdn.net/ljk168/article/details/79441327)

# MongoDB springboot集成mongoDB的使用
在springboot中对mongodb和redis等NoSql数据库都已经有集成了,这里主要学习如何去使用springboot框架集成的mongodb。  
首先导入依赖:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
</dependencies>
```
打开mongodb副本集，创建study数据库，并为数据库创建用户名密码。
在springboot的配置文件中添加mongodb的配置：
```properties
#单节点配置
#spring.data.mongodb.uri=mongodb://user:pwd@ip:port/database
#集群配置
spring.data.mongodb.uri=mongodb://root:123456@192.168.226.130:27017,192.168.226.130:27018,192.168.226.130:27019/study
```
mongodb的增删改查：
+ 创建实体类book
```java
@Data
@Accessors(chain = true)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String content;
    private BigDecimal price;
}
```
+ 创建mongoService接口：
```java
public interface MongoService<T> {

    void save(T t);

    void delete(Long id);

    Long update(T t);

    T queryForOne(T t);
}
```
+ 实现接口：
```java
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
```
+ 测试：
```
    @Test
    void test() {
        mongoService.save(new Book().setId(1L).setName("java").setPrice(BigDecimal.valueOf(50.55)).setContent("hello java"));
        mongoService.save(new Book().setId(2L).setName("c").setPrice(BigDecimal.valueOf(55.55)).setContent("hello c"));
        mongoService.update(new Book().setId(1L).setName("python").setContent("hello python").setPrice(BigDecimal.valueOf(60.01)));
        Book book = mongoService.queryForOne(new Book().setName("python"));
        System.out.println(book.toString());
        mongoService.delete(1L);

    }
```
mongodb增删改查实现完成。
可以在mongodb中使用shell命令查看数据是否正确。
```shell script
rs0:PRIMARY> use study
switched to db study
rs0:PRIMARY> db.auth("root","123456")
1
rs0:PRIMARY> show collections
book
system.indexes
rs0:PRIMARY> db.book.find()
{ "_id" : NumberLong(2), "name" : "c", "content" : "hello c", "price" : "55.55", "_class" : "com.xiazhi.mongodb.bean.Book" }
```
