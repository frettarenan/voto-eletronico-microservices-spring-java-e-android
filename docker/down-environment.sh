#!/bin/bash
docker-compose -f ../docker-compose.yml -f ../docker/portainer/docker-compose.yml down --remove-orphans