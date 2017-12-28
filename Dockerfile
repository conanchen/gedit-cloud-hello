FROM alpine:edge
MAINTAINER baeldung.com
RUN apk add --no-cache openjdk8
COPY files/UnlimitedJCEPolicyJDK8/* \
  /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
