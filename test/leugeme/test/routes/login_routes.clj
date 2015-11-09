(ns leugeme.test.routes.login-routes
  (:require [clojure.test :refer :all]
            [leugeme.setup.employee-setup :refer :all]
            [leugeme.routes.employee-routes :refer [employee-follow]]
            [leugeme.routes.login-routes :refer [login]]
            [leugeme.setup.user-setup :refer :all]))

(deftest should-show-my-idols
  (let [user (make-user "todd" "password")
        _ (follow-some-people user 4)
        response (login "todd" "password" {})]
    (println response)
  (is (.contains (:body response) "Following"))))
