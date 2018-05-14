# Spring Mongodb Web

Sample application with Mongodb.

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
