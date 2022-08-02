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
RUN cp swift-5.6.2-RELEASE-ubuntu20.04/usr/bin/swift /usr/bin/




RUN python --version
RUN java --version
RUN swift --version


# COPY . .
# RUN ./gradlew assembleDist
# # Stage the Doctor distro
# COPY /build/distributions/*.tar .
# RUN tar -xf ./Doctor*.tar && rm -rf ./Doctor*.tar

# # Expose port 8080 and run the Doctor server
# EXPOSE 8080
# CMD ./Doctor*/bin/Doctor
