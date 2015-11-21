-- name: employee-unshare-job!
-- Allows an employee to unshare an employers job
DELETE FROM employee_jobs where employee_id = :employeeid and job_id = :jobid

-- name: employee-share-job!
-- Allows an employee to share an employers job
INSERT INTO employee_jobs
(employee_id, job_id)
VALUES (:employeeid, :jobid)

-- name: employee-follow!
-- Allows someone to follow someone
INSERT INTO employee_follow
(employee_id, user_id)
VALUES (:employeeid, :userid)

-- name: get-all-idol-jobs
-- get all the jobs your idol shared
SELECT j.*, e.id as employee, emp.name as employer, e.first_name, e.last_name
from employee_follow ef
inner join employees e on ef.employee_id = e.id
inner join employee_employer ee on ee.employee_id = e.id
inner join employers emp on ee.employer_id = emp.id
inner join employee_jobs ej on ej.employee_id = ef.employee_id
inner join jobs j on j.id = ej.job_id
where user_id = :userid

-- name: get-employee-jobs
-- get all the jobs an employee has shared
SELECT j.*, ej.share_date, emp.name as employer_name FROM jobs j
inner join employee_employer ee on ee.employer_id = j.employer_id
inner join employers emp on ee.employer_id = emp.id
inner join employee_jobs ej on ej.employee_id = ee.employee_id and j.id = ej.job_id
where ee.employee_id = :employeeid

-- name: get-available-jobs-by-employee 
-- get all the jobs by employer
SELECT j.*, ej.share_date, emp.name as employer_name FROM jobs j
inner join employee_employer ee on ee.employer_id = j.employer_id
inner join employers emp on ee.employer_id = emp.id
left join employee_jobs ej on ej.employee_id = ee.employee_id and j.id = ej.job_id 
where ee.employee_id = :employeeid

-- name: get-job-by-id
-- get the job details
SELECT * FROM jobs where id = :id

-- name: get-employee
-- get the employee by id
SELECT * FROM employees 
where id = :id

-- name: get-my-idols
-- get all the people I admire
SELECT e.* FROM employees e
join employee_follow ef on ef.employee_id = e.id
where ef.user_id = :userid

-- name: get-user
-- get the user trying to login
SELECT * FROM employees
where userid = :userid and password = :password

-- name: find-all-employees-by-name-or-userid
-- gets all the employees in the db
SELECT * FROM employees
where first_name like :search or last_name like :search
