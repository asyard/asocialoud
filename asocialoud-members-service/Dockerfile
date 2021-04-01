FROM java:8-jre
LABEL maintainer="asocialoud"

# like COPY but can also fetch remote URLs, extract TAR files, etc...
ADD ./target/asocialoud-members-service.jar /app/

WORKDIR /usr/app

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app/asocialoud-members-service.jar"]

EXPOSE 7070