# Spring Mongodb Web

Sample application with Mongodb.

## For Windows Environment

For this example I have the following information about Mongo installation.

```
db version v3.2.8
git version: ed70e33130c977bda0024c125b56d159573dbaf0
OpenSSL version: OpenSSL 1.0.1p-fips 9 Jul 2015
allocator: tcmalloc
modules: none
build environment:
    distmod: 2008plus-ssl
    distarch: x86_64
    target_arch: x86_64
```

Try this simple configuration for mongo instance.
Create a configuration file under `C:/developer-resources/mongo-db` with name `mongo.config`.

```
##store data here
dbpath=C:/developer-resources/mongo-db/data

##all output go here
logpath=C:/developer-resources/mongo-db/log/mongo.log

##log read and write operations
diaglog=3
```

Execute this command to start the MongoDB service.

```
mongod --config c:\developer-resources\mongo-db\mongo.config
```

To install `mongod` as a Windows Service use this command:

```
sc.exe create MongoDB binPath= "\"C:\Program Files\MongoDB\Server\3.2\bin\mongod.exe\" --service --config=\"C:\developer-resources\mongo-db\mongo.config\"" DisplayName= "MongoDB" start= "auto"
```

And if you want to add some description for MongoDB service created use this command:

```
sc description MongoDB "NoSQL database for giant ideas"
```

## Inspired on

- https://www.djamware.com/post/59b606e280aca768e4d2b13b/spring-boot-mvc-data-and-mongodb-crud-java-web-application
- https://www.mkyong.com/spring-boot/spring-boot-spring-data-mongodb-example/
