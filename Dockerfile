FROM ubuntu:20.04

RUN apt-get update && apt-get install -y software-properties-common gcc && \
    add-apt-repository -y ppa:deadsnakes/ppa

# Installing Python 3.10.2
RUN apt-get update -y && apt-get upgrade -y && apt-get install -y python3.10
RUN ln -s /usr/bin/python3.10 /usr/bin/python

# Install Java 11
#ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get install -y openjdk-11-jdk


# Install Swift 5.6.2
RUN apt-get install -y wget binutils git gnupg2 libc6-dev libcurl4 libedit2 libgcc-9-dev libpython2.7 libsqlite3-0 libstdc++-9-dev libxml2 libz3-dev pkg-config tzdata zlib1g-dev
RUN wget https://download.swift.org/swift-5.6.2-release/ubuntu2004/swift-5.6.2-RELEASE/swift-5.6.2-RELEASE-ubuntu20.04.tar.gz
RUN tar -xzf swift-5.6.2-RELEASE-ubuntu20.04.tar.gz
ENV PATH="swift-5.6.2-RELEASE-ubuntu20.04/usr/bin:${PATH}"



# Install Kotlin
RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN apt-get -qq -y install curl unzip zip

RUN curl -s "https://get.sdkman.io" | bash
RUN source "$HOME/.sdkman/bin/sdkman-init.sh"
RUN sdk install kotlin


# Testing
RUN kotlin --version
RUN python --version
RUN java --version
RUN swift --version

# Running the Ktor application
FROM gradle:7-jdk11 AS build
COPY  . /src
WORKDIR /src
RUN gradle shadowJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /src/build/libs/Doctor*.jar /app/doctor.jar
ENTRYPOINT ["java","-jar","/app/doctor.jar"]

