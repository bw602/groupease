CREATE USER groupease_sql PASSWORD 'changeit';
CREATE DATABASE groupease_db WITH OWNER groupease_sql;

\connect groupease_db

\i database/initial-schema.sql
\i database/initial-data.sql

GRANT SELECT ON ALL SEQUENCES IN SCHEMA public TO groupease_sql;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO groupease_sql;
