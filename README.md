# Meet je loopsnelheid
This project is part of an assignment for the Hogeschool Utrecht, University of Applied Sciences.

## Prerequisites
Although it is recommended to always use the latest stable version
of Java, this project requires a version of Java 11 or higher.
You can customize this in your compilation settings and `pom.xml`.

For [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html),
you can use your IDE, install it [globally](https://maven.apache.org/download.cgi), 
or use the supplied `mvnw` or `mvnw.cmd`.


## Project setup with Docker
Although not supported on every system,
Development is easiest with [Docker](https://docs.docker.com/desktop/). 
If Docker is installed, 
you can start the database by executing
`docker-compose up` from the commandline 
(or `docker-compose start` to run it in the background), 
while the current directory is the root of this project.
Docker will then start a PostgreSQL image with
the configuration stated in the `docker-compose.yml`
and in `development/db`.

If something goes wrong starting docker-compose, and you
wish to rebuild your image, do so with `docker-compose up --build -V`.

This creates an admin user with the username and password `admin`
and `admin` and a user, password and database for the application,
all called `loopsnelheid-db`.


### Troubleshooting Docker
If you already have PostgreSQL running in the background
and you don't want to stop that process, 
you will have to change the port-binding from `15432:5432` 
to something else, for instance `25432:5432` in `docker-compose.yml`
and in `src/resources/application.properties`. Don't forget to 
change this in your development environment's database client as well.

If data does not seem to be persisted when restarting the
database, make sure Docker has a volume assigned to it.
Add the project's directory or parent directory 
under Docker's `Settings > Resources > File Sharing`.

If you cannot seem to connect to the database,
make sure Docker is allowed to use the host's network.

If all else fails, setup your database manually according 
to the steps explained above. Remember: it is OK to ask for help!

## Booting
First, make sure the database is set up, started and reachable.

Start the application via your IDE by running the `LoopsnelheidApplication`
class. Alternatively, run `mvn spring-boot:start`.
