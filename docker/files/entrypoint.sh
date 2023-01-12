#!/bin/bash

set -eo pipefail

CREDENTIALS_PATH="${CREDENTIALS_PATH:-/neeraj/etc/priv/credentials}"
if [ -f "$CREDENTIALS_PATH/envvars" ]
then
  echo "Loading Vault secrets as Environment variables"
  source "$CREDENTIALS_PATH/envvars"
fi

echo "Environment variables for Datadog agent:"
echo "  DD_TRACE_ENABLED: ${DD_TRACE_ENABLED:-true}"
echo "  DD_PROFILING_ENABLED: ${DD_PROFILING_ENABLED:-false}"
echo "  DD_JMXFETCH_ENABLED: ${DD_JMXFETCH_ENABLED:-true}"
echo "  DD_SERVICE: $DD_SERVICE"
echo "  DD_WF_HOSTNAME: $DD_HOSTNAME"
echo "  DD_VERSION: $DD_VERSION"
echo "  DD_AGENT_HOST: ${DD_AGENT_HOST:-localhost}"
echo "  DD_TRACE_AGENT_PORT: ${DD_TRACE_AGENT_PORT:-8126}"
echo "  DD_JMXFETCH_STATSD_PORT: ${DD_JMXFETCH_STATSD_PORT:-8125}"
echo "  DD_KAFKA_CLIENT_PROPAGATION_ENABLED: ${DD_KAFKA_CLIENT_PROPAGATION_ENABLED:-true}"

echo "Running Service with active profile: $ENVIRONMENT"

if [ "$ENVIRONMENT" = "dev" ] || [ "$ENVIRONMENT" = "prod" ]; then
  exec \
      java \
      -javaagent:/opt/datadog/dd-java-agent.jar \
      -Ddd.trace.global.tags=hostname:"$DD_HOSTNAME" \
      -XX:MinRAMPercentage=50.0 \
      -XX:MaxRAMPercentage=80.0 \
      -XshowSettings:vm \
      -XX:+HeapDumpOnOutOfMemoryError \
      -Dcom.sun.management.jmxremote \
      -Dcom.sun.management.jmxremote.port=5000 \
      -Dcom.sun.management.jmxremote.local.only=false \
      -Dcom.sun.management.jmxremote.authenticate=false \
      -Dcom.sun.management.jmxremote.ssl=false \
      -XX:+UseG1GC \
      -XX:+UseNUMA \
      -XX:MaxGCPauseMillis=100 \
      -XX:ParallelGCThreads=20 \
      -XX:ConcGCThreads=5 \
      -Xlog:gc \
      -jar \
      -Dspring.profiles.active="$ENVIRONMENT" \
      /app/build/app.jar
else
  exec \
      java \
      -XX:MinRAMPercentage=50.0 \
      -XX:MaxRAMPercentage=80.0 \
      -XshowSettings:vm \
      -XX:+HeapDumpOnOutOfMemoryError \
      -Dcom.sun.management.jmxremote \
      -Dcom.sun.management.jmxremote.port=5000 \
      -Dcom.sun.management.jmxremote.local.only=false \
      -Dcom.sun.management.jmxremote.authenticate=false \
      -Dcom.sun.management.jmxremote.ssl=false \
      -XX:+UseG1GC \
      -XX:+UseNUMA \
      -XX:MaxGCPauseMillis=100 \
      -XX:ParallelGCThreads=20 \
      -XX:ConcGCThreads=5 \
      -Xlog:gc \
      -jar \
      -Dspring.profiles.active="$ENVIRONMENT" \
      /app/build/app.jar
fi
