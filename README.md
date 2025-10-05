# Geb Container Sample Application
A sample application using the [Geb Container](https://github.com/cbmarcum/geb-container/) library for integration testing.

## Geb Functional Testing using Testcontainers

Geb Containers integrates [Apache Geb](https://groovy.apache.org/geb/) with [Testcontainers](https://testcontainers.com/) to make it easy to write functional tests for your applications and utilize browsers running in testcontainers and optionally record the browser using a VNC testcontainer and/or capture reporting screenshots and HTML.

## Usage

This sample project is an example of functional web tests using Geb Container in the `integration-test` sources, Apache Geb tests in the `test` sources, and an application to run in the `main` source set.

Each source set has a GebConfig.groovy in `resources` that is configured with an `environments` block. These environments are used to specify which browser type to use for the test run.  The default browser when not specified is `firefox`.  Configured environments are `firefox`, `chrome`, and `edge`.
Firefox is generally available on most platforms which is why we configured it as the default.

To run all tests with firefox on Unix/Linux:
```shell
./gradlew check
```
To run all tests with Chrome:
```shell
./gradlew check -Dgev.env=chrome
```
On Windows you can omit the leading `./`

The `main` source set contains the sample application being tested.  It starts a simple Java file server running on port `8080` and serves files from `src/main/resources/webroot`.

Run with:
```shell
./gradlew run
```
Open a browser to `http://localhost:8080` to view the test page. Stop with `CTRL-C`

The `itegrationTest` starts and stops this application during testing.

## More Information

See [Geb-Container](https://github.com/cbmarcum/geb-container/) and [Geb](https://groovy.apache.org/geb/) for how to use build.gradle and GebConfig.groovy to configure all other options.
