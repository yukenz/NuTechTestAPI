FROM azul/zulu-openjdk-alpine:latest

WORKDIR /app

COPY . .

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]