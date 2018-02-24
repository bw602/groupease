CREATE TABLE Channel (
  id BIGSERIAL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  description TEXT NULL,
  createdOn TIMESTAMP NOT NULL,
  lastUpdatedOn TIMESTAMP NOT NULL
);

INSERT INTO Channel (
  name,
  description,
  createdOn,
  lastUpdatedOn
) VALUES (
  'CS6675-Spring-2018',
  'Advanced Internet Applications - Spring 2018',
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP
);

INSERT INTO Channel (
  name,
  description,
  createdOn,
  lastUpdatedOn
) VALUES (
  'CS8803-BDS-Fall-2017',
  'Big Data Systems and Analytics - Fall 2017',
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP
);
