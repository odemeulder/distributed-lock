# Research into using distributed locks in Java

## Background

One way to achieve a distributed log is through [Redis](https://redis.io/) and the [Redisson](https://github.com/redisson/redisson) Java library.

More information here: https://github.com/redisson/redisson/wiki/8.-distributed-locks-and-synchronizers

## Prerequisite: have Redis running (locally)

[Download instructions](https://redis.io/download)

Run the server:
```bash
redis-server
```
Redis CLI:
```bash
redis-cli
# testing
127.0.0.1:6379>SET olivier EX 10
127.0.0.1:6379>GET olivier
# after Redisson creates a lock, it uses a Hash to store locks, retrieve as such
127.0.0.1:6379>HGETALL odm
1) "050261a9-9735-45e4-ad15-52c36f7fe2b9:55"
2) "1
```

## Run application

Application is configured such that a lock is obtained. It will automatically unlock after 10 seconds. If  another process tries to obtain the lock and it is locked, it will wait one second and give up.

In this app, I created 2 controllers using the same lock (based on the name). As an excercise, you could create another app, similar to this, run it on a different port (8081) and use the exact same lock.

An unlock endpoint is also provided. (`http://localhost:8080/unlock`)

Run application and test
```bash
./gradlew bootRun
curl http://localhost:8080/
Greetings from Springboot!
curl http://localhost:8000/
Could not obtain lock
curl http://localhost:8000/crazy
Crazy not obtain lock
curl http://localhost:8000/crazy
Crazy!
```
