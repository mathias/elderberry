(ns elderberry.core
  (:require [cljs.nodejs :as node]
            [elderberry.routing :refer [make-dispatcher GET POST]]
            [elderberry.utils :as util]))

(def http (node/require "http"))
(def https (node/require "https"))
(def url (node/require "url"))

(defn- merge-params [request params]
  (assoc request :params (merge (:params request {}) params)))

(defn- ssl-opts? [opts]
  (and
   (or (get opts :cert)
       (get opts :certificate))
   (get opts :key)))

(defn create-server [handler-opts]
  (let [opts (get handler-opts :opts)]
    ;; TODO: create an SSL server through https module
    ;; (if (ssl-opts? opts) https http)
    (.createServer http (get handler-opts :handler-fn))))

(defn run-http [handler opts]
  (let [server (create-server {:handler-fn handler
                               :opts opts})]
    (.listen server (:port opts))
    server))

(defn make-request [req]
  {:method (.-method req)
   :url (.-url req)})

(def dispatcher
  (make-dispatcher
   [(GET "/" (fn [req res] (.end res "index")))
    (GET "/users" (fn [req res] (.end res "users")))
    (POST "/users" (fn [req res] (.end res "user created.")))]))

(defn main [& args]
   (let [opts {:port 3000}]
    (run-http (fn [req res]
                (let [request (make-request req)]
                  (dispatcher request res))) opts)
    (util/log "Server started at http://localhost:%s" (:port opts))))

(set! *main-cli-fn* main)
