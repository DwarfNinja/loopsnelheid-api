FROM openjdk:17-jdk-alpine as BUILD
WORKDIR /home/app
COPY . /home/app
RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=BUILD /home/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]