ARG GRADLE_OPENJDK_IMAGE_NAME
ARG GRADLE_OPENJDK_VERSION
ARG OPENJDK_JRE_IMAGE_NAME
ARG OPENJDK_JRE_VERSION
ARG GRADLE_OPTS
ARG ENVIRONMENT

FROM ${GRADLE_OPENJDK_IMAGE_NAME}:${GRADLE_OPENJDK_VERSION} as build
WORKDIR /app/build
COPY . .
RUN gradle build -x test --no-daemon --info

FROM ${OPENJDK_JRE_IMAGE_NAME}:${OPENJDK_JRE_VERSION}

WORKDIR /neeraj/app
ARG GID
ARG GROUP="www"
ARG UID="1001"
ARG USER="neeraj"

ARG VERSION
ARG JAR_FILE
ENV version=${VERSION}
ENV GID ${GID}
ENV GROUP ${GROUP}
ENV UID ${UID}
ENV USER ${USER}

LABEL \
    com.neeraj.app="Java Sample" \
    com.neeraj.description="This is a sample java application with all excamples." \
    com.neeraj.maintainer="Neeraj" \
    com.neeraj.vendor="Neeraj LLC." \
    com.neeraj.version=${version}

RUN groupadd -g "$GID" "$GROUP" && \
    useradd -m -s "$SHELL" -N -G 100,"$GID" -u "$UID" "$USER"

COPY --from=build --chown=1001:80 /app/build/build/libs/javasamples.jar /app/build/app.jar

COPY --chown=1001:80 ./docker/files/entrypoint.sh /app/

RUN chmod +x /app/build/app.jar
RUN chmod +x /app/entrypoint.sh

WORKDIR /app
USER $USER
ENTRYPOINT ["/app/entrypoint.sh"]