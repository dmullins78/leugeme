CREATE TABLE employees
(id bigserial primary key,
  userid VARCHAR(20),
  password VARCHAR(20),
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  email VARCHAR(30),
  last_login TIME default now())
