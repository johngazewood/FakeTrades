FROM nginx
RUN cat /etc/hosts
#RUN rm /etc/nginx/conf.d/default.conf
#RUN rm /etc/nginx/conf.d/examplessl.conf

COPY dist/faketrades-website/* /usr/share/nginx/html/
COPY src/environments/test/nginx.conf /etc/nginx/nginx.conf
#COPY conf /etc/nginx
