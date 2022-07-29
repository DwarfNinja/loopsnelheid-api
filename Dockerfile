FROM openjdk:17-jdk-alpine as build
WORKDIR /home/app
COPY . /home/app
RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-alpine as api
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]