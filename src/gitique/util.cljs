(ns gitique.util
  (:require [clojure.string :as string]))

(enable-console-print!)

(extend-type js/NodeList ISeqable (-seq [array] (array-seq array 0)))
(extend-type js/DOMTokenList ISeqable (-seq [array] (array-seq array 0)))

(defn log [s] (println s) s)
(defn child-text [parent selector] (.-textContent (qs selector parent)))

(defn qs
  "Wrapper for `querySelector`"
  ([selector]
   (qs selector js/document))
  ([selector element]
   (.querySelector element selector)))

(defn qsa
  "Wrapper for `querySelectorAll`"
  ([selector]
   (qsa selector js/document))
  ([selector element]
   (.querySelectorAll element selector)))