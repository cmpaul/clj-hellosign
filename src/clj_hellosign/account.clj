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

(ns clj-hellosign.account
  "Functions for interacting with HelloSign accounts."
  (:use clj-hellosign.core)
  (:require [clj-hellosign.util :as util]))

(defn get-account
  "Creates a GET request for a user account.
  Execute with core/execute get-account."
  []
  {:operation :get-account})

(defmethod execute :get-account [op-data]
  (util/get-request *hellosign-api-key* (str api-root "/account")))

(defn callback-url
  "Creates the data representation of an account callback URL"
  [u] {"callback_url" u})

(defn post-account
  "Updates your account settings. Currently this is limited
  to your account callback URL.
  Execute with core/execute post-account."
  [& extra-info]
  (apply util/merge-maps {:operation :post-account} extra-info))

(defmethod execute :post-account [op-data]
  (util/post-request *hellosign-api-key* (str api-root "/account")
  (dissoc op-data :operation)))

(defn email
  "Creates a data representation of an email address"
  [e] {"email_address" e})

(defn create-account
  "Creates a new HelloSign account that is associated with
  a given email address.
  Execute with core/execute create-account."
  [& extra-info]
  (apply util/merge-maps {:operation :create-account} extra-info))

(defmethod execute :create-account [op-data]
  (util/post-request *hellosign-api-key* (str api-root "/account/create")
  (dissoc op-data :operation)))

(defn verify-account
  "Verifies whether a HelloSign account exists for the given email.
  NOTE: This method is restricted to paid API users.
  Execute with core/execute verify-account"
  [& extra-info]
  (apply util/merge-maps {:operation :verify-account} extra-info))

(defmethod execute :verify-account [op-data]
  (util/post-request *hellosign-api-key* (str api-root "/account/verify")
  (dissoc op-data :operation)))
