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

(ns clj-hellosign.account-test
  (:use [clj-hellosign core account])
  (:require [clojure.test :refer :all]))

; API key used for testing
(def test-api-key (config :hellosign-test-key))

(deftest get-account-test
  (testing "GET /account"
    (testing "operation"
      ; Make sure the get-account fn returns the correct operation
      (def get-account-op (get-account))
      (is (= get-account-op {:operation :get-account})))
    (if (not (nil? test-api-key))
      (testing "result"
        ; Make the call to GET /account
        (def get-account-result (with-api-key test-api-key (execute get-account-op)))
        ; Make sure the account object is readable and items are accessible
        (def account-object (get get-account-result :account))
        (is (not (nil? account-object)))
        (is (not (nil? (get account-object :email_address)))))
      (testing "no result"
        (prn "No API key, skipping test")
        (is (= 1 1))))))

(deftest post-account-test
  (testing "POST /account"
    (testing "operation"
      (def test-callback-url "https://www.google.com")
      (def post-account-op (post-account (callback-url test-callback-url)))
      (is (= post-account-op {:operation :post-account, "callback_url" test-callback-url})))
    (if (not (nil? test-api-key))
      (testing "result"
        (def post-account-result
          (with-api-key test-api-key
            (execute post-account-op)))
        (def account-object (get post-account-result :account))
        (is (not (nil? account-object)))
        (is (= test-callback-url (get account-object :callback_url))))
      (testing "no result"
        (prn "No API key, skipping test")
        (is (= 1 1))))))

(deftest create-account-test
  (testing "POST /account/create"
    (testing "operation"
      (def test-email (str "clj-test-" (quot (System/currentTimeMillis) 1000) "@example.com"))
      (def create-account-op (create-account (email test-email)))
      (is (= create-account-op {:operation :create-account, "email_address" test-email})))
    (if (not (nil? test-api-key))
      (testing "result"
        (def create-account-result
          (with-api-key test-api-key
            (execute create-account-op)))
        (def account-object (get create-account-result :account))
        (is (not (nil? account-object)))
        (is (= test-email (get account-object :email_address))))
      (testing "no result"
        (prn "No API key, skipping test")
        (is (= 1 1))))))
