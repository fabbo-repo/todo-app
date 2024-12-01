FROM nginx:1.27.3-alpine

WORKDIR /app
COPY entrypoint.sh entrypoint.sh

COPY ./build /usr/share/nginx/html

COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

ENTRYPOINT [ "/app/entrypoint.sh" ]
