FROM azul/zulu-openjdk:latest
ENV APPDIR /app
WORKDIR $APPDIR
COPY . .
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]