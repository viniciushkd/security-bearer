FROM openjdk:11
RUN mkdir app
ARG JAR_FILE
ADD /target/bearer-0.0.1-SNAPSHOT.jar /app/bearer-0.0.1-SNAPSHOT.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/bearer-0.0.1-SNAPSHOT.jar"]