#!/bin/sh

set -e

WEB_FILES_DIR="/usr/share/nginx/html"
ENV_VARS="TASK_API_BASE_URL FIREBASE_API_KEY FIREBASE_PROJECT_ID FIREBASE_SENDER_ID FIREBASE_SENDER_ID"

find "$WEB_FILES_DIR" -name "*.js" | while read -r WEB_FILE; do
    for key in $ENV_VARS; do
        value=$(eval echo \$$key)

        sed -i "s|__ENV_VITE_${key}|${value}|g" "$WEB_FILE"
    done
done

exec /docker-entrypoint.sh nginx -g "daemon off;" "$@"
