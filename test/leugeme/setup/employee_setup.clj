(ns leugeme.setup.employee-setup
  (require [clojure.java.jdbc :as j]
           [leugeme.setup.dbconnection :refer [test-db]]
           [environ.core :refer [env]]))

(defn get-shared-jobs-by-employee [employeeid] 
  (j/query test-db 
           ["select * from jobs j 
            inner join employee_jobs e on j.id = e.job_id 
            where e.employee_id = ?" employeeid]))

(defn get-arbitrary-job-by-employer [employerid] 
  (:id (first (j/query test-db 
           ["select * from jobs j 
            where j.employer_id = ?" employerid]))))

(defn get-arbitrary-job-by-employee [employeeid] 
  (:id (first (j/query test-db 
           ["select * from jobs j 
            inner join employee_employer e on j.employer_id = e.employer_id 
            where e.employee_id = ?" employeeid]))))

(defn make-job 
  "Make a test job"
  [empid job] 
  (j/insert! test-db :jobs { :employer_id empid :name job}))

(defn insert-employer [name]
  (first (j/insert! test-db :employers {:name name })))

(defn make-employer 
  "This function makes test employers"
  [details]
  (let [ employer (insert-employer (:name details))]
    (doseq [job (:jobs details)] 
      (make-job (:id employer) job))
    (:id employer))) 

(defn insert-employee [name] 
  (first (j/insert! test-db :employees
                    {:first_name name 
                     :last_name name 
                     :email name })))

(defn you-are-hired! [employer employee]
  (j/insert! test-db :employee_employer
             {:employee_id employee 
              :employer_id employer}))

(defn make-employee
  "This function makes test employees and optionally associates to employers"
  [name & [employers]]
  (let [employee (insert-employee name)]
    (doseq [employer employers]
      (let [emp-id (make-employer employer)] 
        (you-are-hired! emp-id (:id employee))))
    employee)) 
