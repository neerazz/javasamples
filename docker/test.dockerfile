ARG GRADLE_OPENJDK_IMAGE_NAME
ARG GRADLE_OPENJDK_VERSION
FROM ${GRADLE_OPENJDK_IMAGE_NAME}:${GRADLE_OPENJDK_VERSION} as prepare

WORKDIR /app/build

COPY build.gradle settings.gradle gradlew ./
ENV GRADLE_OPTS -Dorg.gradle.jvmargs=-Xmx2048M -Dorg.gradle.daemon=false
# docker layer caching for dependencies
RUN gradle resolveNonTestDependencies --no-daemon --info
FROM prepare as build

COPY . .
RUN gradle build -x test --no-daemon --info

EXPOSE 8080
RUN chmod +x /app/entrypoint.sh

ENTRYPOINT ["gradle build"]
