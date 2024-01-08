FROM eclipse-temurin:17-alpine

RUN adduser -D -g AppUser -h /app -s /sbin/nologin appuser && apk upgrade -U --no-cache

WORKDIR /app

USER appuser

COPY --chown=appuser:appuser target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]