(ns elderberry.routing)

(defn route-matches?
  [method url req]
  (and (= url (.-url req))
       (= method (.-method req))))

(defn create-route
  [method url handler]
  (fn [continue]
    (fn [req res]
      (if (route-matches? method url req)
        (handler req res)
        (continue req res)))))

(defn GET [url handler] (create-route "GET" url handler))
(defn PUT [url handler] (create-route "PUT" url handler))
(defn POST [url handler] (create-route "POST" url handler))
(defn DELETE [url handler] (create-route "DELETE" url handler))

(defn make-dispatcher [routes]
  ((apply comp (conj routes
                     (fn [continue]
                       (fn [req res]
                         (.writeHead res 404)
                         (.end res "Not found")))))))
