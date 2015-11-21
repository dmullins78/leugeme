delete from employee_employer;
delete from employees;
delete from employers;
delete from jobs;
delete from employee_follow;
delete from employee_jobs;

with empees as (
  insert into employees (userid, password, first_name, last_name, email, description) values ('dmullins', 'password', 'Dan', 'Mullins', 'dan@mullins.com', 'I like programming in Java!')
  RETURNING id as employee_id
  ), empers as (
  insert into employers (name) values ('FHLB')
  RETURNING id as employer_id
)

insert into employee_employer 
select employee_id, employer_id from empees
cross join empers;

insert into jobs (employer_id, name, description) values ((select max(id) from employers), 'Developer 1', '1st Sweet opportunity');
insert into jobs (employer_id, name, description) values ((select max(id) from employers), 'Developer 2', '2nd Sweet opportunity');

insert into employers (name) values ('Company X1');
insert into employees (userid, password, first_name, last_name, email, description) values ('other1', 'password', 'Alpha', 'User', 'dan@mullins.com', 'I like programming in Ruby!');
insert into employee_employer (employer_id, employee_id) values ((select max(id) from employers), (select max(id) from employees));
insert into jobs (employer_id, name, description) values ((select max(id) from employers), 'Job X1', '1st Sweet opportunity');
insert into employee_jobs (employee_id, job_id) values ((select max(id) from employees), (select max(id) from jobs)); 
insert into jobs (employer_id, name, description) values ((select max(id) from employers), 'Job X2', '2nd Sweet opportunity');
insert into employee_jobs (employee_id, job_id) values ((select max(id) from employees), (select max(id) from jobs)); 

insert into employers (name) values ('Company Y1');
insert into employees (userid, password, first_name, last_name, email, description) values ('other2', 'password', 'Beta', 'User', 'dan@mullins.com', 'I like programming in Clojure!');
insert into employee_employer (employer_id, employee_id) values ((select max(id) from employers), (select max(id) from employees));
insert into jobs (employer_id, name, description) values ((select max(id) from employers), 'Company Y1', '1st Sweet opportunity');
insert into employee_jobs (employee_id, job_id, share_date) values ((select max(id) from employees), (select max(id) from jobs), now()); 
insert into jobs (employer_id, name, description) values ((select max(id) from employers), 'Company Y2', '2nd Sweet opportunity');
insert into employee_jobs (employee_id, job_id, share_date) values ((select max(id) from employees), (select max(id) from jobs), now()); 
