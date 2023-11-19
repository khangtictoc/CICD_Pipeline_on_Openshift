FROM 10.98.86.99:8085/library/openjdk:17-alpine

WORKDIR /tmp/mkyong-project
COPY target/*.jar /tmp/mkyong-project/webapp.jar

WORKDIR logs
CMD ["/bin/sh", "-c", "java -jar /tmp/mkyong-project/webapp.jar"]