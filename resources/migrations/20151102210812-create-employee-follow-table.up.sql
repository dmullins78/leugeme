create table employee_follow (
  employee_id bigserial, 
  user_id bigserial
);

ALTER TABLE employee_follow ADD PRIMARY KEY (employee_id, user_id);
