FROM --platform=linux/amd64 azul/zulu-openjdk:21
ENV APPDIR /app
WORKDIR $APPDIR
COPY . .
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]