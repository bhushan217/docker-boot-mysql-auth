FROM tomcat:9.0.41-jdk11-adoptopenjdk-openj9
#9.0.41-jdk11-adoptopenjdk-openj9
#mcr.microsoft.com/java/tomcat:8-zulu-alpine-tomcat-9
#ADD ../../../target/docker-boot-mysql-auth-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/docker-boot-mysql-auth.war
ADD target/docker-boot-mysql-auth-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
##FROM maven:3.6.3-jdk-11-slim
###
##ARG MAVEN_VERSION=3.6.3
#### [...]
##VOLUME ["/usr/src/app", "/root/.m2"]
##WORKDIR /usr/src/app
###
### copy pom.xml from context into image
###COPY src /usr/src/app/src
###COPY pom.xml /usr/src/app/pom.xml
###COPY target /usr/src/app/target
###
#### run from /app directory which now contains a pom.xml, should work
##RUN ls /usr/src/app
###RUN mvn package -DskipTests
###RUN ls /usr/src/app/target
##
##EXPOSE 8080 5005
###RUN mvn spring-boot:run
##ARG JAR_FILE=target/*.jar
##COPY ${JAR_FILE} docker-boot-mysql-auth-0.0.1-SNAPSHOT.jar
##ADD /target/docker-boot-mysql-auth-0.0.1-SNAPSHOT.jar docker-boot-mysql-auth.jar
##ENTRYPOINT ["java", "-jar", "docker-boot-mysql-auth.jar", "in.b2k.B2kApplication"]