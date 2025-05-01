<!--
    #/**
    # * @author Avdhut Shirgaonkar
    # * Email: avdhut.ssh@gmail.com
    # * LinkedIn: https://www.linkedin.com/in/avdhut-shirgaonkar-811243136/
    # */
    #/***************************************************/
-->
# Test Automation Framework for Cart Offer Service

## 📑 Table of Contents

- [Introduction](#-introduction)
- [Prerequisites](#️-prerequisites)
- [Project Structure](#-project-structure)
- [Getting Started](#️-getting-started)
- [Test Execution](#-test-execution)
- [Reporting](#-reporting)
- [Future Enhancements](#-future-enhancements)
- [Contacts](#-contacts)

## 📖 Introduction

This repository contains a REST API test automation framework developed using REST Assured and Java, managing dependencies with Maven. The framework is designed to automate test cases for the Cart Offer API service, which applies different types of discounts (FLATX, FLATP) based on restaurant, user and segment eligibility.

The framework follows best practices such as:
- **Page Object Model (POM)** approach adapted for API testing
- **Factory pattern** for request/response specification building
- **Reusable components** for common operations
- Comprehensive **test reporting** using Allure
- **Explicit assertions** and validation points

The framework supports different test types:
- Smoke tests for basic functionality
- Regression tests for detailed coverage
- Integration tests for segment-based offer application
- Negative tests for error handling verification

## 🛠️ Prerequisites

Before you start, ensure you have the following installed on your machine:

- **Java Development Kit (JDK)**: Version 11 or later
- **Maven**: To manage project dependencies
- **Docker**: For running the mock server and service
- **Git**: To clone the repository
- **An IDE**: (such as IntelliJ IDEA or Eclipse) with TestNG plugin installed

## 📁 Project Structure

The project follows a modular structure optimized for API testing:

```plaintext
src
├── main
│   └── java
│       └── com
│           └── automation
│               └── SampleCartOffer
│                   └── pojo
│                       ├── AddOffer.java       # POJO for offer creation request
│                       └── ApplyOffer.java     # POJO for offer application request
├── test
│   ├── java
│   │   └── com
│   │       └── automation
│   │           └── SampleCartOffer
│   │               ├── Utils
│   │               │   ├── AllureReportUtils.java   # Utility for Allure reporting
│   │               │   ├── AssertionUtils.java      # Centralized assertion methods
│   │               │   └── PropertyReader.java      # Configuration properties reader
│   │               ├── specs
│   │               │   ├── RequestSpecificationBuilder.java  # Request spec builder
│   │               │   └── ResponseSpecificationBuilder.java # Response spec builder
│   │               └── tests
│   │                   ├── _00_BaseTest.java        # Base test with common methods
│   │                   ├── _01_smokeTest.java       # Smoke tests
│   │                   ├── _02_RegressionTest.java  # Regression tests
│   │                   ├── _03_IntegrationTest.java # Integration tests
│   │                   └── _04_NegativeTest.java    # Negative tests
│   └── resources
│       └── application.properties      # Framework configuration
└── testng.xml                         # Suite Execution file
└── mockserver                         # Mock server configuration
    └── docker-compose.yml            # Docker setup for mock service
```
## ▶️ Getting Started

1. Clone the repository:

```bash
git clone https://github.com/avdhutssh/sample-cart-offer_Automation.git
```

2. Navigate to the project directory:

```bash
cd sample-cart-offer_Automation
```

3. Start the mock server:

```bash
cd mockserver
docker compose up
```
The mock server will start at port 1080.

4. Build and start the application:

```bash
./mvnw clean install -DskipTests

java -jar target/simple-springboot-app-0.0.1-SNAPSHOT.jar
```
The application server will start at port 9001.

This will download all required dependencies such as RestAssured, TestNG, ExteAllurent Reports, and Log4j.

## 🚀 Test Execution

You can run the test cases using TestNG directly from the IDE or from the command line:

1. Using Maven Command Line

Run all tests:

```bash
./mvnw test
```

Run a specific test class::

```bash
./mvnw test -Dtest=com.automation.SampleCartOffer.tests._01_smokeTest
```

Run tests using TestNG XML:

```bash
./mvnw test -DsuiteXmlFile=testng.xml
```

2. Using Batch File


```bash
 ./generate-report.bat
```

## 🎯 Reporting

This project integrates Allure Reports for detailed reporting of test executions.

To view the reports:

After test execution, generate the Allure report:
```bash
mvn allure:report
```

To view the report in a browser:
```bash
mvn allure:serve
```

You can also capture screenshots for failed test cases and view them in the Extent Report.

![Allure Report](/Misc/AllureReport.png)


## 🤖 CI/CD Using Jenkins

### 1. Jenkins Integration

You can integrate the project with Jenkins for Continuous Integration. Follow these steps:

1. Install Maven, Allure, TestNg, HTML Publisher plugins
2. Set up a Maven Project Dashboard in Jenkins.
3. Clone the GitHub repository under Source Code Management
4. In the Build section, add the following command to run the tests:
   ```bash
   mvn test
   ```

![Jenkins-Execution](/Misc/Jenkins.png)


## 🔮 Future Enhancements
Data Driven Testing

## 📧 Contacts

- [![Email](https://img.shields.io/badge/Email-avdhut.ssh@gmail.com-green)](mailto:avdhut.ssh@gmail.com)
- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue)](https://www.linkedin.com/in/avdhut-shirgaonkar-811243136/)

Feel free to reach out if you have any questions, or suggestions.

Happy Learning!

Avdhut Satish Shirgaonkar