#!/bin/bash
mvn clean install
docker build -t frettarenan/ve-api-gateway .