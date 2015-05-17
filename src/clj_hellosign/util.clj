; This file contains methods that were built by Alberto Bengoa
; for the stripe clojure binding: https://github.com/abengoa/clj-stripe

; Copyright (c) 2011 Alberto Bengoa. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file epl-v10.html at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.

(ns clj-hellosign.util
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(defn- remove-nulls
  "Removes from a map the keys with nil value."
  [m]
  (into {} (remove (comp nil? second) m)))

(defn merge-maps
  "Merges several maps into one, removing any key with nil value."
  [& maps]
  (remove-nulls (reduce into {} maps)))

(defn- append-param
  "Appends a URL parameter to a string."
  [s name value]
  (if value
    (let [print-param (fn [n v] (str n "=" v))]
      (if (and s (> (count s) 0))
        (str s "&" (print-param name value))
        (print-param name value)))
    s))

(defn url-with-optional-params
  "If parameters are provided, creates a parametrized URL as
  originalurl?param1name=param1value&param2name=param2value&..."
  [url m [& param-names]]
  (let [params-str (reduce #(append-param %1 %2 (get m %2 nil)) nil param-names)]
    (str url (if params-str (str "?" params-str) ""))))

(defn post-request
  "POSTs to a URL using the provided API key and parameters."
  [api-key url params]
  (try
    (let [result (client/post url
      {
        :insecure? true ; FIXME: Make this conditional
        :basic-auth [api-key]
        :query-params params :throw-exceptions false
      })]
      (json/read-json (:body result)))
  (catch java.lang.Exception e e)))

(defn get-request
  "GETs a URL using the provided API key and parameters."
  [api-key url]
  (try
    (let [result (client/get url
      {
        :insecure? true ; FIXME: Make this conditional
        :basic-auth [api-key] :throw-exceptions false
      })]
      (json/read-json (:body result)))
  (catch java.lang.Exception e e)))
