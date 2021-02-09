# ------------ local Build & Deploy to OpenShift
1. oc login --token...
2. from project root issue PowerShell:
./mvnw clean package "-Dmaven.test.skip=true" "-Dquarkus.kubernetes.deploy=true"

# ------------------------------------------


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

If running with another Quarkus service in local dev mode you should specify a non-standard debug port to avoid contention.
mvnw compile quarkus:dev -Ddebug=5006

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `getting-started-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/getting-started-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/getting-started-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

# Swagger
http://localhost:8180/q/swagger-ui/
