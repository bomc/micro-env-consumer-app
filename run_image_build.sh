#!/bin/sh

echo set docker daemon environment variables.
eval $(minikube docker-env)

echo run command 'docker build -t localhost:5000/bomc/consumer:v0.0.1'

docker build -t localhost:5000/bomc/consumer:v0.0.1 .

docker push localhost:5000/bomc/consumer