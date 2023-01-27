FROM maven:3.8.7-openjdk-18 as build
WORKDIR /build
COPY . .
RUN mvn clean package -X -DskipTests


FROM openjdk:18
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=build ./build/target/*.jar ./application.jar
EXPOSE 8080

ENTRYPOINT java -jar application.jar
