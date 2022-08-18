# Doctor

This project was originally meant as a way to allow code written in Swift to be executed safely on a remote server. The
project has since taken on a new life to allow all modern languages to be executed on a server in a safe manner. Thus,
removing the need for people to have an expensive computer to compile code.

**Disclaimer**: This project is only meant to be a _base_. It does not contain all the necessary security features to
combat arbitrary code execution. Please **_fork and harden_** the code before you use it in production.
___

## Technology

We use Ktor as it's a fast and easy to learn framework for server creation. Exposed is used to make working with the
database easier and offer a layer of protection. This project is meant to run inside
a Docker instance to prevent any malicious code from hurting your server instance.
___

## How you can support

The number one way to support this project is by donating to [Empowr](https://empowrco.org). If you are technically
inclined
and would like to submit code, then feel free to contribute to any area on the following list.

- Security. While this serves only serves as a base, we would like to ensure it is as secure as possible.
- More Languages. We would love to support more languages on the server side.
- Bugs & Features. We are open to adding new features. So if you have a cool idea, create it!

___

## How it works by the module

### Command

The command module is responsible for running commands locally on the server hardware

#### Commander

This file is used for executing commands locally. You simply pass in the command
to your `Commander`'s `execute()` method

### Executor
The purpose of this module is for executing code.

#### Executor

The executor interface is meant to be generic enough that any language could have its own implementation.
We currently support a Swift implementation and look to support Kotlin and Python in the future.

Here is a list of the executors and the language(s) they support.

| Files           | Languages     |
|-----------------|---------------|
| SwiftExecutor   | Apple's Swift |
| Kotlin Executor | Kotlin        |
| PythonExecutor  | Python        |


### Playground

Students may want to download the code they wrote in an easily executable way.

A list of supported downloadable executables.

- Swift

This module contains an api `/download` that if hit, will package up the code sent to it, and return a fully working swift playground.

### Routing-Utils
This module contains helper methods for authentication. Authentication is very simple by just checking if a Secret key 
passed in the header matches what's stored on the database. Feel free to change this module for your authentication needs.

### buildSrc
This is a module used solely for dependency management.

## Docker Build

**Note** - Uncomment the line `ARG DEBIAN_FRONTEND=noninteractive` in the Dockerfile if the build hangs

To build and tag a new docker image, navigate to the root of this repository and run:
```console
docker build . --platform <platform> -t empowerco/doctor:<tag_name>
```
Where:
 - `<platform>` is the target platform that the image will run on, usually `linux/x86_64` or `--platform linux/amd64` if running on an M1 Mac.
 - `<tag_name>` is a unique string tag.

Example:
```console
docker build . --platform linux/x86_64 -t empowerco/doctor:latest
```

To run the built docker image as a container locally, do:
```console
docker run -p 8080:8080 empowerco/doctor:<tag_name>
```

This command includes a port-forward argument that will forward your machine's 8080 to 8080 on the container.
Navigate to `http://localhost:8080` to hit the server. (For a quick check, try `http://localhost:8080/health`)
