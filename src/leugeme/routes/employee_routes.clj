(ns leugeme.routes.employee-routes
  (:require [leugeme.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [clojure.string :refer [blank?]]
            [leugeme.db.core :as db]))

(defn job-in-list? [jobid employerjobs]
  (= jobid (get-in (first employerjobs) [:id])))

(defn show-employee-jobs [employeeid]
  (let [employee (db/get-employee {:id employeeid})
        jobs (db/get-employee-jobs {:employeeid employeeid})]
    (layout/render "employee_jobs.html" {:jobs jobs :employee (first employee)})))

(defn show-employee-share-jobs [employeeid]
  (let [employee (db/get-employee {:id employeeid})
        jobs (db/get-available-jobs-by-employee {:employeeid employeeid})]
    (layout/render "employee_job_listing.html" {:jobs (group-by :employer_name jobs) :employee (first employee)})))

(defn employee-share-job [employeeid jobid]
  (let [employerjobs (db/get-available-jobs-by-employee {:employeeid employeeid})]
    (if (job-in-list? jobid employerjobs)
      (db/employee-share-job! {:employeeid employeeid :jobid jobid})))
  (layout/render "employee-job-shared.html"))

(defn employee-unshare-job [employeeid jobid]
  (let [employerjobs (db/get-available-jobs-by-employee {:employeeid employeeid})]
    (if (job-in-list? jobid employerjobs)
      (db/employee-unshare-job! {:employeeid employeeid :jobid jobid})))
  (layout/render "employee-job-shared.html"))

(defn employee-follow [employeeid {session :session}]
  (db/employee-follow! {:employeeid employeeid :userid (:id (:user session))})
  (layout/render "employee-followed.html"))

(defn show-employee-search []
  (layout/render "all-employees.html" {:employees []}))

(defn do-employee-search [search]
  (let [employees (db/find-all-employees-by-name-or-userid {:search (str "%" search "%")})]
    (layout/render "all-employees.html" {:employees employees})))

(defroutes employee-routes
  (POST "/employee/:employeeid/job/:jobid/share" [employeeid jobid] (employee-share-job (Integer. employeeid) (Integer. jobid)))
  (POST "/employee/:employeeid/job/:jobid/unshare" [employeeid jobid] (employee-unshare-job (Integer. employeeid) (Integer. jobid)))
  (POST "/employee/:employeeid/follow" [employeeid :as req] (employee-follow (Integer. employeeid) req ))
  (GET "/employee/:id/jobs" [id] (show-employee-share-jobs (Integer. id)))
  (GET "/employee/:id" [id] (show-employee-jobs (Integer. id)))
  (GET "/employees" [] (show-employee-search))
  (POST "/employees" [search] (do-employee-search search)))
