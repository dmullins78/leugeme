(ns leugeme.routes.login-routes
  (:require [leugeme.layout :as layout]
            [ring.util.response :refer [response]]
            [ring.util.http-response :refer [found]]
            [compojure.core :refer [defroutes GET POST]]
            [leugeme.db.core :as db]))

(defn show-login []
  (layout/render "login.html"))

(defn login [id password {session :session}]
  (let [user (db/get-user {:userid id :password password})]
    (if (not-empty user) 
      (-> (found "/home")
          (assoc :session  (assoc session :user (first user))))
      (layout/render "login.html" {:invalid true}))))

(defn show-home [{session :session}]
  (let [ idols (db/get-my-idols {:userid (:id (:user session))})]
    (layout/render "user-feeds.html" {:idols idols})))

(defroutes login-routes
  (GET "/login" [] (show-login))
  (GET "/home" [:as req] (show-home req))
  (POST "/login" [id password :as req] (login id password req)))
