# Loopsnelheid API

This project is part of a medical research conducted by the _Kenniscentrum Gezond en Duurzaam Leven_ of the _Hogeschool Utrecht, University of Applied Sciences_.


## Prerequisites

#### Java

Although it is recommended to always use the latest stable version of Java, this project requires a version of [Java 11](https://www.oracle.com/java/technologies/downloads/) or higher. You can customize this in your compilation settings and `pom.xml`.

#### Maven

For [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html), you can use your IDE, install it [globally](https://maven.apache.org/download.cgi), or use the supplied `mvnw` or `mvnw.cmd`.

#### GNU Make

To easily create packages of the project, you can use [GNU Make](https://www.gnu.org/software/make/). The easiest method of installation would be by using [Chocolatey](https://community.chocolatey.org/) after installation, you can execute the command `choco install make` in your command line terminal to install _GNU Make_, but _GNU Make_ can also be downloaded from the _GNU_ website.


## Project setup with Docker

Although not supported on every system, Development is easiest with [Docker](https://docs.docker.com/desktop/). If Docker is installed, you can start the database by executing `docker-compose up` from the commandline (or `docker-compose start` to run it in the background), while the current directory is the root of this project. Docker will then start a PostgreSQL image with the configuration stated in the `docker-compose.yml` and in `development/db`.

If something goes wrong starting docker-compose, and you wish to rebuild your image, do so with `docker-compose up --build -V`.

This creates an admin user with the username and password `admin` and `admin` and a user, password and database for the application, all called `loopsnelheid-db`.
 
### Troubleshooting Docker

If you already have PostgreSQL running in the background, and you don't want to stop that process, you will have to change the port-binding from `15432:5432` to something else, for instance `25432:5432` in `docker-compose.yml` and in `src/resources/application.properties`. Don't forget to change this in your development environment's database client as well.

If data does not seem to be persisted when restarting the database, make sure Docker has a volume assigned to it. Add the project's directory or parent directory under Docker's `Settings > Resources > File Sharing`.

If you cannot seem to connect to the database, make sure Docker is allowed to use the host's network.

If all else fails, set up your database manually according to the steps explained above.

### Setting up application properties

To set up the `application.properties` file there are multiple templates you can use:
- **DEV** (For a development build that containerizes the API and uses a mailcatcher mail server): `/application-templates/[DEV]app_properties.txt`
- **DEV-API** (For a development build that does **not** containerize the API and uses a mailcatcher mail server): `/application-templates/[DEV-API]app_properties.txt`
- **PROD** (For a production build that containerizes the API and uses a dedicated mail server): `/application-templates/[DEV-API]app_properties.txt`, this file must be edited to add the `security.jwt.secret`, `spring.mail.username` and `spring.mail.password`. These values can be requested at the client/employer.


## Launching the application

First, make sure the database is set up, started and reachable.

Start the application via your IDE by running the `LoopsnelheidApplication`
class. Alternatively, run `mvn spring-boot:start`.


## Packaging the project

Make sure that the `application.properties` file coincides with the type of package you want to make, **DEV** or **PROD**, take a look at [Setting up application properties](#setting-up-application-properties) for more info. To package the project we use _GNU Make_, enter the command: `make TYPE=<type> VERSION=<version>` to start packaging the project. An example of this command is: `make TYPE=prod VERSION=v1.0.0`. After _GNU Make_ has finished packaging the project you can find your package on the organisation’s GitHub page.


