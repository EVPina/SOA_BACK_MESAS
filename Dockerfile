FROM eclipse-temurin:17-jdk-alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY --chown=appuser:appgroup target/soamesas-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
USER appuser
ENTRYPOINT ["java", "-jar", "app.jar"]