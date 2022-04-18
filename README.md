# Doctor
Using the Doctor to keep Apple away.
___
## Purpose
Empowr's program teaches iOS development to high school students of color.
Since the average iOS developer earns six figures, we end the poverty cycles that plague our community.
Unfortunately, MacBooks are required for iOS development and MacBooks are also very expensive.
Creating a server that will allow arbitrary swift code execution will allow our students to complete Playground style code exercies,
without a MacBook computer.

The end goal of this server is to completely remove the MacBook requirement for our students.

## Technology
We use Ktor as it's a fast and easy to learn framework for server creation. This project is meant to run inside
a Docker instance to prevent any malicious code from hurting your server instance.

## How it works by the module

### Command
The command module is responsible for running commands locally on the server hardware

#### Commander
This file is used for executing commands locally. You simply pass in the command
to your `Commander`'s `execute()` method

### Executor
The purpose of this module is for executing code.

#### Executor
The executor interface is meant to be generic enough that any language could have it's own implementation.
We currently support a Swift implementation and look to support Kotlin and Python in the future.

#### SwiftExecutor
To execute Swift code just use the `SwiftExecutor`. Pass in the code to the execute method and it will create a `.swift`
file, capture the output, and return it

### Playground
Students may want to download a the code they wrote in an easily executable way.
We currently only have plans to support Swift for this module, but may extend functionality in the future.

This module contains an api `/download` that if hit, will package up the code sent to it, and return a fully working swift playground.

### Routing-Utils
This module contains helper methods for authentication. Authentication is very simple by just checking if a Secret key 
passed in the header matches what's stored on the database. Feel free to change this module for your authentication needs.

### buildSrc
This is a module used solely for dependency management.

## Docker Build
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
