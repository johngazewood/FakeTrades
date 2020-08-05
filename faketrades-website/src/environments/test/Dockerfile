FROM nginx

#RUN rm /etc/nginx/conf.d/default.conf
#RUN rm /etc/nginx/conf.d/examplessl.conf

COPY dist/faketrades-website/* /usr/share/nginx/html/
COPY src/environment/test/nginx.conf /etc/nginx/nginx.conf
#COPY conf /etc/nginx
