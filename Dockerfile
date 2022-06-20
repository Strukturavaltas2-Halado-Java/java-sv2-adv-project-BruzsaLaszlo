FROM openjdk:18
WORKDIR /opt/app
COPY target/*.jar /opt/app/inventory.jar
CMD ["java", "-jar", "/opt/app/inventory.jar"]