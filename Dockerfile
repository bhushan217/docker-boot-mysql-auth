FROM maven:3.6.3-jdk-11-slim
#
#ARG MAVEN_VERSION=3.6.3
## [...]
#WORKDIR /usr/src/app
#
## copy pom.xml from context into image
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app/pom.xml
#
## run from /app directory which now contains a pom.xml, should work
#RUN  mvn clean package

EXPOSE 8080 5005
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} springboot-mysql-docker-compose-0.0.1-SNAPSHOT.jar
##ADD /target/springboot-mysql-docker-compose-0.0.1-SNAPSHOT.jar springboot-mysql-docker-compose-0.0.1-SNAPSHOT.jar
##target/springboot-mysql-docker-compose-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java", "-jar", "springboot-mysql-docker-compose-0.0.1-SNAPSHOT.jar", "in.b2k.Application"]