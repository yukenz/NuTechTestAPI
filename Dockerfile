FROM openjdk:19-jdk AS builder
ENV APPDIR /app
WORKDIR $APPDIR
COPY . .
RUN ./gradlew build && echo "done build jar package"

FROM openjdk:19-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]