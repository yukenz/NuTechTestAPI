FROM azul/zulu-openjdk-alpine:latest AS build

WORKDIR /build

COPY . .

RUN ./gradlew build jar   # Jika Anda menggunakan Gradle

FROM azul/zulu-openjdk-alpine:latest

WORKDIR /app

COPY --from=build '/build/build/libs/ppob-0.0.1-SNAPSHOT.jar' '/app/ppob-0.0.1-SNAPSHOT.jar'

EXPOSE 8080

CMD ["java", "-jar", "nama-aplikasi.jar"]