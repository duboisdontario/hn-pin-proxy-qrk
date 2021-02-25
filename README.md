## Service Dependencies
++++++++++++++++++++++++++++++
# Dependent services
This service relies on two REST services:
- *TBD* DB2 REST service.
Ensure these are working before testing this service.

# Windows command synatx (cmd)
Note: The following scripts use Windows command. For PowerShell prefix commands using ./ syntax and wrap switch arguments in double quotes.

## Local development - Running the application in dev mode
You can run your application in dev mode that enables live coding using:
```shell script
mvnw compile quarkus:dev
```
NOTE: If you run multiple apps locally be sure to use "-Ddebug=5006" switch to avoid debug collision.

# RUN Config - environment variables MUST be set in application.properties or passed as -D switch variables using the keys below:
DB2_ENDPOINT = DB2 service endpoint.

DB2_UID - DB2 RACF ID.

DB2_PWD - DB2 RACF password.

Local testing - pass these in as -D switch variables when issuing 'mvnw clean quarkus:dev'.
OpenShift deployment - pass these in as -p switch variables when issuing 'oc new-app...'.

## Creating a native OpenShift image
https://quarkus.io/guides/maven-tooling.html.
# building native - CDI requirements
Ensure the pom.xml has a 'native' property
```shell script
<quarkus.native.additional-build-args>-H:ReflectionConfigurationFiles=reflection-config.json</quarkus.native.additional-build-args>
```
When you build natively for OpenShift you must add class config to reflection-config.json for CDI to work.
Also, default interface methods don't work so you must make them 'public static'.

# ------------ OpenShift image build
Once local testing is complete build a native image for OpenShift.
1. 
```shell script
oc login --token...
```
2. from project root issue native build command:
```shell script
mvnw clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -Dquarkus.container-image.push=true
```
Note: PowerShell requires quotes around -D switches.

Quarkus images - https://quarkus.io/guides/container-image#s2i
OpenShift Client (oc) commands - https://docs.openshift.com/container-platform/4.6/cli_reference/openshift_cli/developer-cli-commands.html

# --------------- OpenShift create app and deploy
If an application (service, deployment, route) doesn't exist perform the following, otherwise OpenShift will reuse existing objects and modify them accordingly.

3. create service, deployment objects in OpenShift...
(be sure to 'oc login' using a valid token and set the correct project/namespace)
```shell script
oc get is
```
(make note of the image name for use in 'oc new-app' command below)

Create a service if it doesnt exist...!!! BE SURE TO PASS ENV VARIABLES IN USING -p SWITCHES)
```shell script
oc new-app --name=<artifactId> --image-stream="<artifactId>:<tag>"
oc get svc
```
4. create route
```shell script
oc expose svc <artifactId>
oc describe svc <artifactId>
```

5. edit route to use TLS
```shell script
oc patch route <artifactId> -p "{\"spec\":{\"tls\":{\"termination\":\"edge\"}}}"
```

6. test the URL. If it's inaccessible check the target port of the service matches your Quarkus port in application.properties. Default Quarkus port is 8080.

7. export and review all config
```shell script
oc get -o yaml is,bc,dc,svc,route,pvc -l app=<label>  >  <servicename>_dev.yaml
```        

# Push local image to OpenShift imageStream
```shell script
docker push default-route-openshift-image-registry.apps.ocp2.pc.uat.ocp.gocloud.gov.on.ca/prsoocpa-0000-dev/hn-pin-proxy:<tag>
```

# ------------------------------------------
Building+Deployment - https://redhat-developer-demos.github.io/quarkus-tutorial/quarkus-tutorial/basics.html

# Healthcheck
https://redhat-developer-demos.github.io/quarkus-tutorial/quarkus-tutorial/health.html

<endpoint>/health
<endpoint>/health/live
<endpoint>/health/ready

# Swagger - port and swagger config in application.properties
http://localhost:8080/q/swagger-ui/

on OpenShift dev - https://<projectURL>/q/swagger-ui