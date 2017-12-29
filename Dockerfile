FROM frolvlad/alpine-oraclejdk8
LABEL maintainer="https://github.com/conanchen"
RUN find . -type f
RUN ls -R .
COPY gedit-cloud-hello-0.0.1-SNAPSHOT.jar /opt/gedit-cloud/lib/
ENV SPRING_APPLICATION_JSON='{"spring": {"cloud": {"config": {"server": \
    {"git": {"uri": "/var/lib/spring-cloud/config-repo", "clone-on-start": true}}}}}}'
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/gedit-cloud/lib/gedit-cloud-hello-0.0.1-SNAPSHOT.jar"]
VOLUME /var/lib/gedit-cloud/hello-repo
EXPOSE 8088 8980
