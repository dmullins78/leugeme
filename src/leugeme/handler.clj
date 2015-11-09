(ns leugeme.handler
  (:require [compojure.core :refer [defroutes routes wrap-routes]]
            [leugeme.layout :refer [error-page]]
            [leugeme.routes.home :refer [home-routes]]
            [leugeme.routes.employee-routes :refer [employee-routes]]
            [leugeme.routes.login-routes :refer [login-routes]] 
            [leugeme.middleware :as middleware]
            [leugeme.db.core :as db]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.3rd-party.rotor :as rotor]
            [selmer.parser :as parser]
            [environ.core :refer [env]]))

(defn init
  "init will be called once when
  app is deployed as a servlet on
  an app server such as Tomcat
  put any initialization code here"
  []

  (timbre/merge-config!
    {:level     (if (env :dev) :trace :info)
     :appenders {:rotor (rotor/rotor-appender
                          {:path "leugeme.log"
                           :max-size (* 512 1024)
                           :backlog 10})}})

  (if (env :dev) (parser/cache-off!))
  (db/connect!)
  (timbre/info (str
                 "\n-=[leugeme started successfully"
                 (when (env :dev) " using the development profile")
                 "]=-")))

(defn destroy
  "destroy will be called when your application
  shuts down, put any clean up code here"
  []
  (timbre/info "leugeme is shutting down...")
  (db/disconnect!)
  (timbre/info "shutdown complete!"))

(def app-routes
  (routes
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (wrap-routes #'employee-routes middleware/wrap-csrf)
    (wrap-routes #'login-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
