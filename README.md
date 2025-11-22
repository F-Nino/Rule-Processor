# FAFSA Rule Processor

A Spring Boot application that validates FAFSA (Free Application for Federal Student Aid) applications against a set of business rules.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)

## Prerequisites

This project requires **Java 17**. You can use any Java 17 distribution, but this project leverages [asdf](https://asdf-vm.com/) for version management.

### Option 1: Using asdf (Recommended)

If you already have asdf installed with the Java plugin, simply run:

```bash
asdf install
```

The `.tool-versions` file in this repository will automatically configure Java 17.

#### First-time asdf setup:

```bash
# Install asdf
brew install asdf

# Add Java plugin
asdf plugin add java https://github.com/halcyon/asdf-java.git

# Install Java 17
asdf install java corretto-17.0.17.10.1

# Set JAVA_HOME (add to ~/.zshrc)
. ~/.asdf/plugins/java/set-java-home.zsh

# Reload shell
source ~/.zshrc
```

### Option 2: Using Other Java Managers

Any Java 17 installation will work. Verify your Java version:

```bash
java -version
```

Expected output should show Java 17.x.x.

## Getting Started

1. **Install Maven**
   ```bash
   brew install maven
   ```

2. **Clone the repository**
   ```bash
   git clone https://github.com/F-Nino/Rule-Processor.git
   cd Rule-Processor
   ```

## Running Tests

Run all tests:

```bash
mvn test
```

Run specific test class:

```bash
mvn test -Dtest=ValidationServiceTest
```

## Running the Application

1. **Package the application**
   ```bash
   mvn clean package
   ```

2. **Run the JAR**
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

The application will start on `http://localhost:8080`.

## API Documentation

### Validate Application

**Endpoint:** `POST /applications/validate`

### Test with cURL

**Valid Application Example:**
```bash
curl --location 'localhost:8080/applications/validate' \
--header 'Content-Type: application/json' \
--data '{
  "studentInfo": {
    "firstName": "Jane",
    "lastName": "Smith",
    "ssn": "123456789",
    "dateOfBirth": "2003-05-15"
  },
  "dependencyStatus": "dependent",
  "maritalStatus": "single",
  "household": {
    "numberInHousehold": 4,
    "numberInCollege": 1
  },
  "income": {
    "studentIncome": 5000,
    "parentIncome": 65000
  },
  "stateOfResidence": "CA"
}'
```

**Response:**
```json
{
  "valid": true,
  "violations": []
}
```

**Invalid Application Example:**
```bash
curl --location 'localhost:8080/applications/validate' \
--header 'Content-Type: application/json' \
--data '{
  "studentInfo": {
    "firstName": "John",
    "lastName": "Doe",
    "ssn": "invalid",
    "dateOfBirth": "2015-01-01"
  },
  "dependencyStatus": "dependent",
  "maritalStatus": "married",
  "household": {
    "numberInHousehold": 2,
    "numberInCollege": 5
  },
  "income": {
    "studentIncome": -1000
  },
  "stateOfResidence": "XX"
}'
```

**Response:**
```json
{
  "valid": false,
  "violations": [
    {
      "fieldName": "studentInfo.ssn",
      "message": "SSN must be exactly 9 digits (invalid)"
    },
    {
      "fieldName": "studentInfo.dateOfBirth",
      "message": "Student too young (10 years old, < 14)"
    },
    {
      "fieldName": "spouseInfo",
      "message": "Spouse information is required for married students"
    },
    {
      "fieldName": "household",
      "message": "Number in college (5) cannot exceed number in household (2)"
    },
    {
      "fieldName": "income",
      "message": "Student income cannot be negative (-1000)"
    },
    {
      "fieldName": "income.parentIncome",
      "message": "Parent income is required for students with dependent status"
    },
    {
      "fieldName": "stateOfResidence",
      "message": "Invalid state code: XX"
    }
  ]
}
```

## Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/        # REST controllers
│   │   ├── model/             # Domain models (using Lombok & Records)
│   │   ├── rules/             # Validation rule implementations
│   │   └── service/           # Business logic services
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/example/demo/
        ├── controller/        # Integration tests
        ├── rules/             # Rule unit tests
        └── service/           # Service unit tests
```

## Validation Rules

The application implements the following validation rules:

1. **Student Age Rule** - Validates student is at least 14 years old
2. **SSN Format Rule** - Validates SSN is 9 digits
3. **State Code Rule** - Validates state of residence is a valid US state/territory
4. **Marital Status Rule** - Validates spouse information for married students
5. **Dependent Parent Income Rule** - Validates parent income for dependent students
6. **Household Logic Rule** - Validates household numbers are logical
7. **Income Validation Rule** - Validates income values are non-negative

## Technology Stack

- **Java 17**
- **Spring Boot 3.5.8**
- **Maven** - Dependency management
- **Lombok** - Reduce boilerplate code
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **AssertJ** - Fluent assertions

## Design Decisions

See [Decisions.md](./Decisions.md) for detailed architectural decisions and rationale.