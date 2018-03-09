# Groupease

Create groups with ease.

[![Waffle.io - Columns and their card count](https://badge.waffle.io/Groupease/groupease.svg?columns=all)](https://waffle.io/Groupease/groupease)

<!--------------------------------------------------------------------------------------------------------------------->

## How To Contribute

[See Contributing Guidelines](CONTRIBUTING.md).

<!--------------------------------------------------------------------------------------------------------------------->

## Getting Started

<!--------------------------------------------------------------------------------------------------------------------->

### Setting Up IDE

If using IntelliJ, "Import Project", and select the build.gradle file.
This will build and set up IntelliJ for the project.

<!--------------------------------------------------------------------------------------------------------------------->

### Local Setup

Running Groupease locally requires the Postgres Database to be set up.
[Read about the Groupease DB Scripts](db/README.md).

To specify local environment variables for heroku local, create a `.env` file
in your project's top level directory (same directory as this README).
The `.env` file is in the `.gitignore` file so it will not be committed. 

~~~~shell
JDBC_DATABASE_URL='jdbc:postgresql://localhost:5432/groupease_db?user=groupease_sql&password=changeit'
PORT=8080
~~~~

<!--------------------------------------------------------------------------------------------------------------------->

### Building

To build the war file containing the server and client code,
run the Gradle stage task from your project's top level directory (same directory as this README):

~~~~shell
~/projects/groupease $ ./gradlew stage
~~~~

<!--------------------------------------------------------------------------------------------------------------------->

### Running Locally

Run the latest code that you have built locally with
[the heroku CLI](https://devcenter.heroku.com/articles/heroku-cli):

~~~~shell
~/projects/groupease $ heroku local
~~~~

<!--------------------------------------------------------------------------------------------------------------------->
