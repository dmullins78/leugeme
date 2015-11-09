(ns leugeme.test.routes.employee-routes
  (:require [clojure.test :refer :all]
            [leugeme.setup.employee-setup :as s]
            [leugeme.routes.employee-routes :refer :all]))

(deftest should-show-employer-jobs
  (let [employee (s/make-employee "Ted C" [{:name "FHLB" :jobs ["job 1"]}])
        response (show-employee-share-jobs (:id employee) )]
    (is (.contains (:body response) "job 1" ))
    (is (.contains (:body response) "Ted C"))))

(deftest should-allow-employee-to-share-job
  (let [employee (s/make-employee "Ted C" [{:name "FHLB" :jobs ["job 1"]}])
        some-job-id (s/get-arbitrary-job-by-employee (:id employee)) ]
    (employee-share-job (:id employee) some-job-id)
    (let [employee-shared-jobs (s/get-shared-jobs-by-employee (:id employee))]
      (is (= 1 (count employee-shared-jobs))) 
      (is (= some-job-id (get-in (first employee-shared-jobs) [:id]))))))

(deftest should-not-allow-employee-to-share-other-employers-job
  (let [other-employer-id (s/make-employer {:name "someonelse" :jobs ["job 1"]} )
        other-employer-job-id (s/get-arbitrary-job-by-employer other-employer-id)
        employee (s/make-employee "Ted C" [{:name "FHLB" :jobs ["job 1"]}])]
    (employee-share-job (:id employee) other-employer-job-id)
    (let [employee-shared-jobs (s/get-shared-jobs-by-employee (:id employee))]
      (is (= 0 (count employee-shared-jobs))))))

(deftest should-allow-employee-to-unshare-job
  (let [employee (s/make-employee "Ted C" [{:name "FHLB" :jobs ["job 1"]}])
        some-job-id (s/get-arbitrary-job-by-employee (:id employee)) ]
    (employee-share-job (:id employee) some-job-id)
    (employee-unshare-job (:id employee) some-job-id)
    (let [employee-shared-jobs (s/get-shared-jobs-by-employee (:id employee))]
      (is (= 0 (count employee-shared-jobs))))))

(deftest should-allow-people-to-see-employee-jobs
  (let [employee (s/make-employee "Ted C" [{:name "FHLB" :jobs ["job 1"]}])
        some-job-id (s/get-arbitrary-job-by-employee (:id employee))
        _ (employee-share-job (:id employee) some-job-id)
        response (show-employee-jobs (:id employee)) ]
    (let [employee-shared-jobs (s/get-shared-jobs-by-employee (:id employee))]
      (is (.contains (:body response) "job 1" )))))

(deftest should-be-able-to-follow-employees
  (let [employee (s/make-employee "Ted C" [{:name "FHLB" :jobs ["job 1"]}])
        response (employee-follow  (:id employee) {:session {:user 1234}}) ]
    (is (.contains (:body response) "Employee Followed!" ))))
