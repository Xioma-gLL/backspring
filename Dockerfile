# 1) Usamos una imagen con Java 17 (o la versión que uses)
FROM eclipse-temurin:17-jdk-alpine

# 2) Copiamos el JAR generado por Spring Boot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 3) Puerto que usará Spring Boot
EXPOSE 8080

# 4) Comando para ejecutar la app
ENTRYPOINT ["java","-jar","/app.jar"]
