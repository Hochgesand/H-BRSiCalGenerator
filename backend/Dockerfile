FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:17.0.1-slim
COPY --from=build /home/app/target/hbrsiCalGenerator-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]