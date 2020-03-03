FROM adoptopenjdk:8-jre-hotspot
COPY build/libs/empireJob-0.0.1-SNAPSHOT.jar  app.jar
RUN chmod 777 app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]

