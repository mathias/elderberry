(ns elderberry.utils
  (:require [cljs.nodejs :as node]))

(node/enable-util-print!)

(def log (.-log js/console))
