FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
ENV JAVA_OPTS="--add-opens java.base/java.time=ALL-UNNAMED -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5050"
ENV ARTIFACT="book-service"
ENV VERSION="0.0.1-SNAPSHOT"
COPY ./book-service/build/libs/${ARTIFACT}-${VERSION}.jar app.jar
EXPOSE 8080
EXPOSE 5050
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

