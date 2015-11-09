(ns leugeme.setup.dbconnection)

(def test-db {:classname "org.postgresql.Driver"
               :subprotocol "postgresql" 
               :subname "//localhost:5432/leugeme_test"})
