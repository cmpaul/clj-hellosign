; The MIT License (MIT)
;
; Copyright (C) 2015 hellosign.com
;
; Permission is hereby granted, free of charge, to any person obtaining a copy
; of this software and associated documentation files (the "Software"), to deal
; in the Software without restriction, including without limitation the rights
; to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
; copies of the Software, and to permit persons to whom the Software is
; furnished to do so, subject to the following conditions:
;
; The above copyright notice and this permission notice shall be included in all
; copies or substantial portions of the Software.
;
; THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
; IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
; FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
; AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
; LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
; OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
; SOFTWARE.

(ns clj-hellosign.core
  "Core functions for the HelloSign API"
  (:use [clojure.java.io])
  (:require [clj-hellosign.util :as util]
            [clojure.edn :as edn]))

(defonce ^:dynamic *hellosign-api-key* nil)

(defn load-config-file
  "Loads the clojure data structure defined in the file.
  EDN (extensible data notiation) ensures there is no arbitrary
  code execution."
  [file]
  (try
    (edn/read-string (slurp file))
    (catch java.io.FileNotFoundException e {})))

; Load the configuration file
(defonce config (load-config-file "config.clj"))

; Define the root URL for all API calls
(defonce api-root
  (if (nil? (config :hellosign-api-root))
    "https://api.hellosign.com/v3"
    (config :hellosign-api-root)))

(defmacro with-api-key
  "Binds the specified HelloSign API key to the hellosign-api-key variable and
  executes the function(s) provided."
  [api-key & fns]
  `(binding [*hellosign-api-key* ~api-key] ~@fns))

(defmulti execute
  "Executes a HelloSign API call.
  All operations are defined using other clj-hellosign functions that return
  data structures representing API calls, but that do not execute them. For
  actually making calls to the HelloSign servers, the execute function must be
  used. `execute` expects a HelloSign API key in the context so use the macro
  with-api-key to set the key and wrap one or several execute calls."
  :operation)
