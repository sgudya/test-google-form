# Google Form test

Simple google form tests

## Installing

In order to run tests, [Java](https://www.java.com/en/download/help/download_options.xml) and [Maven](https://maven.apache.org/install.html) should be installed


## Running the tests

Tests can be run by command:
```
mvn clean test -P {browser_name}
```
List of available browsers:

* chrome
* firefox
* ie (only for Windows)
* edge (only for Windows)
* htmlunit
* phantomjs
* safari (only for Mac)

## Test results

Report can be found in ./target/surefire-reports after the test run
