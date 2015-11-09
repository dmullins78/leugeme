(ns leugeme.setup.user-setup
  (:require [clojure.java.jdbc :as j]
            [leugeme.setup.employee-setup :refer [make-employee]]
            [leugeme.setup.dbconnection :refer [test-db]]
            [leugeme.routes.employee-routes :refer [employee-follow]]))

(defn wrap-user-in-session [user]
  {:session {:user (:id (first user))}})

(defn follow-some-people [user following]
  (let [session (wrap-user-in-session user)]
    (loop [x 0]
      (if (< x following) 
        (let [employeeid (make-employee (str "Bill " x) )]
        (employee-follow (:id employeeid) session)
          (recur (inc x)))))))

(defn make-user 
  "I make test users"
  [name password]
  (j/insert! test-db :employees {:userid name :password password :name name} ))
