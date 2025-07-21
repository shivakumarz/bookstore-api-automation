# Project Title

Bookstore API Automation Framework

## Description

This project is a simple Bookstore API built with FastAPI. It allows users to manage books and perform user authentication, including sign-up and login functionalities. The API uses JWT tokens for securing endpoints related to book management.

## Getting Started

Follow the steps below to clone the repository, install dependencies, and run the test suite.

### Prerequisites

- Python 3.7+
- pip

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/sanjay-dandekar-jktech/git
    ```

2. Navigate to the project directory:

    ```bash
    cd bookstore
    ```

3. Install the required packages:

    ```bash
    pip install -r requirements.txt
    ```

### Running the Application

1. Start the FastAPI server:

    ```bash
    uvicorn main:app --reload
    ```

2. The API will be available at `http://127.0.0.1:8000`

## Dependencies

- Python 3.9+
- FastAPI, Uvicorn
- Java 17
- Maven
- RestAssured
- TestNG
- Jackson
- Allure CLI 
- Git 

## Installing

### Clone the repository:

- git clone https://github.com/shivakumarz/bookstore-api-automation

### Navigate to the project directory:

- cd bookstore-api-automation

## Install dependencies:

- mvn clean install

## Executing Program

- Run Tests: mvn clean test -DsuiteXmlFile=testng.xml

- Generate Allure Report: allure serve target/allure-results

## Run with Environment Config:

- mvn clean test -Denv=qa

## Help

- For help and questions, raise an issue or contact the maintainer:
Shiva Kumar