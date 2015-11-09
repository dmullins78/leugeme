create table employee_employer (
  employee_id bigserial, 
  employer_id bigserial
);

ALTER TABLE employee_employer ADD PRIMARY KEY (employee_id, employer_id);
