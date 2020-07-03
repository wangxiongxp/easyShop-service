#!/bin/bash

name="redis"

docker stop ${name}
docker rm ${name}

docker run -idt --name ${name} -p 6379:6379 redis redis-server --appendonly yes

