FROM adoptopenjdk:11-jre-hotspot
ENV TZ=Asia/Seoul

COPY vuestock-config-server-1.0-SNAPSHOT.jar ./app.jar

ENTRYPOINT java ${CONFIG_ARGS} -jar app.jar