FROM adoptopenjdk:14-jre-hotspot as builder
WORKDIR /opt/app
COPY target/*.jar cemetery.jar
RUN java -Djarmode=layertools -jar cemetery.jar extract

FROM adoptopenjdk:14-jre-hotspot
WORKDIR /opt/app
COPY --from=builder /opt/app/dependencies/ ./
COPY --from=builder /opt/app/spring-boot-loader/ ./
COPY --from=builder /opt/app/snapshot-dependencies/ ./
COPY --from=builder /opt/app/application/ ./
ENTRYPOINT ["java", \"org.springframework.boot.loader.JarLauncher"]

