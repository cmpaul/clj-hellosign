# clj-hellosign [![Build Status](https://travis-ci.org/cmpaul/clj-hellosign.svg)](https://travis-ci.org/cmpaul/clj-hellosign)

A Clojure library that provides bindings for the [HelloSign API](https://www.hellosign.com/api/documentation).

## Usage

Add clj-hellosign to the `:dependencies` list in your project.clj file:

```
[cmpaul/clj-hellosign "0.0.1"]
```

All function calls in this wrapper require two things:
1. They must be wrapped with a `with-api-key` command to bind your API key.
2. They must be invoked with an `execute` command.

```clojure
(with-api-key "YOUR_API_KEY"
  (execute fn-1)
  ...
  (execute fn-n))
```

### Accounts

#### GET /account

```clojure
(with-api-key "YOUR_API_KEY"
  (prn (execute get-account)))
```

#### POST /account

```clojure
(with-api-key "YOUR_API_KEY"
  (execute (post-account (callback-url "https://yourcallback.com")))
```

#### POST /account/create

```clojure
(with-api-key "YOUR_API_KEY"
  (execute (create-account (email "new_account@example.com")))
```

#### POST /account/verify

```clojure
(with-api-key "YOUR_API_KEY"
  (execute (verify-account (email "account@example.com")))
```

# TODO
* Finish adding all endpoints!
    * [x] /account
    * [ ] /signature_request
    * [ ] /template
    * [ ] /team
    * [ ] /unclaimed_draft
    * [ ] /embedded
* Deploy to clojars

## License

```
The MIT License (MIT)

Copyright (C) 2015 hellosign.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
