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
    (testing "result"
      ; Make the call to GET /account
      (def get-account-result (with-api-key test-api-key (execute get-account-op)))
      ; Make sure the account object is readable and items are accessible
      (def account-object (get get-account-result :account))
      (is (not (nil? account-object)))
      (is (not (nil? (get account-object :email_address)))))))
