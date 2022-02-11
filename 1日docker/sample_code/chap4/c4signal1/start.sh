#!/bin/sh
handle () {
  echo 'handle sigterm/sigint'
  exit 0
}
trap handle TERM INT

nginx -g "daemon off;" &
wait
