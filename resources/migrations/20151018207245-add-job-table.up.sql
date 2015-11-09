CREATE TABLE jobs (
  id bigserial primary key,
  employer_id bigserial,
  name VARCHAR(30),
  description VARCHAR(600));

create table employee_jobs (
  employee_id bigserial, 
  job_id bigserial
);

ALTER TABLE employee_jobs ADD PRIMARY KEY (employee_id, job_id);
