FROM openjdk:11.0.14.1-buster as build
COPY . .
RUN ./gradlew assembleDist

FROM ubuntu:latest as run
RUN apt-get update -y && apt-get upgrade -y

# Install Java 11
#ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get install -y openjdk-11-jdk
RUN java --version

# Install Swift 5.3.3
RUN apt-get install -y wget binutils git gnupg2 libc6-dev libcurl4 libedit2 libgcc-9-dev libpython2.7 libsqlite3-0 libstdc++-9-dev libxml2 libz3-dev pkg-config tzdata zlib1g-dev
RUN wget https://swift.org/builds/swift-5.3.3-release/ubuntu2004/swift-5.3.3-RELEASE/swift-5.3.3-RELEASE-ubuntu20.04.tar.gz
RUN tar -xzf swift-5.3.3-RELEASE-ubuntu20.04.tar.gz
RUN swift-5.3.3-RELEASE-ubuntu20.04/usr/bin/swift --version

# Stage the Doctor distro
COPY --from=build /build/distributions/*.tar .
RUN tar -xf ./Doctor*.tar && rm -rf ./Doctor*.tar

# Expose port 8080 and run the Doctor server
EXPOSE 8080
CMD ./Doctor*/bin/Doctor
