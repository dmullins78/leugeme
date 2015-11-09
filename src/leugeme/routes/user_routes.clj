(ns leugeme.routes.user-routes
  (:require [leugeme.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [leugeme.db.core :as db]))

(defn show-idols [{session :session}]
  (let [idols (db/get-my-idols {:userid (:user session)})]
    (layout/render "show-idols.html" {:idols idols})))
