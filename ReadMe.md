This project includes login to hepsiburada web site test automation scenarios by using Selenium, Cucumber and BDD framework.

Test scenarios are described in Gherkin format in the feature files located here ./src\test\resources\features

## Installation ##

You need to have [Java 8 JDK]

To run the tests locally with Chrome, install ChromeDriver

To install all dependencies, build


## How to run the tests ##

To run test scenarios press CTRL+F10 or write "gradle cucumber" command to Execute Gradle Task
Tests will run on Chrome. You can run the tests on different browsers by adding the definition of your desired browser to OpenBrowser function in Steps Java Class.

 

## Dependencies
* *[selenium](https://www.selenium.dev/)*
* *[webdrivermanager](https://github.com/bonigarcia/webdrivermanager)*
* *[cucumber](https://cucumber.io/)*
