# openapi-generator-bug3

Minimal reproducible example for [[BUG] On openapi-generator-maven-plugin v7.5.0 not required array fields are initialized](https://github.com/OpenAPITools/openapi-generator/issues/18458).

## Development

This repo is aimed to be opened in **[VSCode](https://code.visualstudio.com/)** with the **[Remote Development](https://code.visualstudio.com/docs/remote/remote-overview)** extension pack installed.

The development is done inside a **[Docker container](https://docker.com/)** that installs and configures the required tools on first run (the first time the Docker image is build might take some time).

Please, make sure you have **VSCode** with the **Remote Development extension pack** and **Docker** installed and working on your system before cloning the respository then clone de repository:

`git clone https://github.com/rubensa/openapi-generator-bug3.git`

For detailed instructions see: **[Developing inside a Container](https://code.visualstudio.com/docs/remote/containers)**.

## Project Organization

    ├── .devcontainer     <- VSCode Remote Development configuration
    │
    ├── src               <- Application source code
    │
    ├── .gitgnore         <- Git ignored files
    ├── pom.xml           <- Maven dependencies
    └── README.md         <- This file

## Showing the bug

You can see the bug by running the following command in a terminal inside the VSCode container:
```shell script
mvn clean compile test
```

This will generate under **target/generated-sources/openapi/src/main/java/org/eu/rubensa/openapi/model/** folder the Java classes based on **src/main/resources/META-INF/resources/open-api/sample.yml** OpenAPI definition file before trying to compile the project.

The generated Java code will be something like:

```java
...
public class SetSampleRequest {

  private String title;

  @Valid
  private List<Long> related = new ArrayList<>();
...
```

and you'll see in the terminal a test failure like:

```bash
...
2024-04-22T14:48:47.717Z  WARN 16345 --- [           main] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.lang.Long> org.eu.rubensa.controller.SampleController.setSample(org.eu.rubensa.openapi.model.SetSampleRequest): [Field error in object 'setSampleRequest' on field 'related': rejected value [[]]; codes [Size.setSampleRequest.related,Size.related,Size.java.util.List,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [setSampleRequest.related,related]; arguments []; default message [related],2147483647,1]; default message [size must be between 1 and 2147483647]] ]

MockHttpServletRequest:
      HTTP Method = POST
      Request URI = /sample
       Parameters = {}
          Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json, application/problem+json", Content-Length:"28"]
             Body = {
  "title": "New sample"
}

    Session Attrs = {}

Handler:
             Type = org.eu.rubensa.controller.SampleController
           Method = org.eu.rubensa.controller.SampleController#setSample(SetSampleRequest)

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = org.springframework.web.bind.MethodArgumentNotValidException

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 400
    Error message = Invalid request content.
          Headers = []
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 1.868 s <<< FAILURE! -- in org.eu.rubensa.controller.SampleControllerTest
[ERROR] org.eu.rubensa.controller.SampleControllerTest.testSetSample_shouldReturnNewId -- Time elapsed: 0.741 s <<< FAILURE!
java.lang.AssertionError: Status expected:<201> but was:<400>
        at org.springframework.test.util.AssertionErrors.fail(AssertionErrors.java:59)
        at org.springframework.test.util.AssertionErrors.assertEquals(AssertionErrors.java:122)
        at org.springframework.test.web.servlet.result.StatusResultMatchers.lambda$matcher$9(StatusResultMatchers.java:637)
        at org.springframework.test.web.servlet.MockMvc$1.andExpect(MockMvc.java:214)
        at org.eu.rubensa.controller.SampleControllerTest.testSetSample_shouldReturnNewId(SampleControllerTest.java:39)
        at java.base/java.lang.reflect.Method.invoke(Method.java:580)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
```

If you modify the `pom.xml` file and change the `openapi-generator-maven-plugin` version to `7.4.0` the generated Java code will be something like:

```java
...
public class SetSampleRequest {

  private String title;

  @Valid
  private List<Long> related;
...
```

and there should be no test errors in the terminal:

```bash
...
2024-04-22T14:53:56.939Z  INFO 17991 --- [           main] o.e.r.controller.SampleControllerTest    : Started SampleControllerTest in 1.378 seconds (process running for 2.266)
OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
WARNING: A Java agent has been loaded dynamically (/home/user/.m2/repository/net/bytebuddy/byte-buddy-agent/1.14.12/byte-buddy-agent-1.14.12.jar)
WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
WARNING: Dynamic loading of agents will be disallowed by default in a future release
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.733 s -- in org.eu.rubensa.controller.SampleControllerTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.132 s
[INFO] Finished at: 2024-04-22T14:53:58Z
[INFO] ------------------------------------------------------------------------
```