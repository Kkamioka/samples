#! /bin/bash

cd `dirname $0`
if [ $# -ne 1 ]; then
  echo "Please provid context name"
  exit 1
fi

CONTEXT=$1
SERVER=`grep "server:" ./config | awk '{print $2}'`
kubectl config set-cluster $CONTEXT --server=$SERVER --insecure-skip-tls-verify=true
kubectl config set-credentials $CONTEXT --client-key=./client.key --client-certificate=./client.crt
kubectl config set-context $CONTEXT --cluster=$CONTEXT --user=$CONTEXT
