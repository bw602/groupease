/* First drop the database owned by our user. */
DROP DATABASE groupease_db;

/* Next, drop any privileges owned by our user. */
DROP OWNED BY groupease_sql;

/* Last, drop our user. */
DROP USER groupease_sql;
