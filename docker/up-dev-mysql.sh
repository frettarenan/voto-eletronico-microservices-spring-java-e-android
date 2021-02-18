#!/bin/bash
docker-compose -f ../docker-compose.yml -f ../docker/mysql/docker-compose.yml -f ../docker/portainer/docker-compose.yml up -d rf-portainer rf-mysql-voto-eletronico