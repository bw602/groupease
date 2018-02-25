# Groupease DB Scripts

## Flyway

Groupease uses [Flyway](https://flywaydb.org/) for managing database changes and versioning.
The Flyway DB scripts are located under the [db/migration](migration) directory in this project.

## Local PostgreSQL

You must have a postgres DB to use when building and running locally.

First, connect to the local DBMS from your project's top level directory:

~~~~shell
~/projects/groupease $ psql postgres
~~~~

Next, initialize the user & database with the local-setup.sql:

~~~~shell
postgres=# \i db/local-setup.sql
~~~~

Last, quit out of the psql shell:

~~~~shell
postgres=# \q
~~~~

After the local database is created, Flyway will take things from here.
