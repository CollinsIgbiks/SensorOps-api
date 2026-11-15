# ---- Stage 1: build the jar with the full JDK ----
FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

# Dependencies first: this layer is reused as long as pom.xml doesn't change
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw -B -q dependency:go-offline

# Then the source code, which changes often
COPY src src
RUN ./mvnw -B -q package -DskipTests

# ---- Stage 2: run it with only the JRE ----
FROM eclipse-temurin:25-jre
WORKDIR /app

RUN groupadd --system app && useradd --system --gid app app
COPY --from=build /workspace/target/*.jar app.jar
USER app

EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]