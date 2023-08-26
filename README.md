# Ktor-E-Commerce Api for Backend. 
[![Ktor](https://img.shields.io/badge/ktor-2.3.3-blue.svg)](https://github.com/ktorio/ktor)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
<a href="https://github.com/piashcse"><img alt="License" src="https://img.shields.io/static/v1?label=GitHub&message=piashcse&color=C51162"/></a>

Ktor-E-Commerce backend built with [ktor](https://ktor.io/docs/welcome.html) framework for e-commerce api development.

## Swagger View

<p align="center">
  <img width="100%" height="40%" src="https://github.com/piashcse/ktor-E-Commerce/blob/master/screenshots/Screenshot-2023-05-13.png" />
</p>

# Main Features
- Role Management(Admin, Seller, User)
- Login
- Registration
- Shop Registration
- Product Category
- Product Subcategory
- Brand 
- Cart
- Order

## Architecture
<p align="center">
  <img width="40%" height="25%" src="https://github.com/piashcse/ktor-E-Commerce/blob/master/screenshots/mvc.png" />
</p>
<p align="center">
<b>Fig.  MVC (Model - View - Controller) design pattern.</b>
</p>

## Built With 🛠
- [Ktor](https://ktor.io/docs/welcome.html) - Ktor is a framework to easily build connected applications – web applications, HTTP services, mobile and browser applications. Modern connected applications need to be asynchronous to provide the best experience to users, and Kotlin Coroutines provides awesome facilities to do it in an easy and straightforward way.
- [Exposed](https://github.com/JetBrains/Exposed) - Exposed is a lightweight SQL library on top of JDBC driver for Kotlin language. Exposed has two flavors of database access: typesafe SQL wrapping DSL and lightweight Data Access Objects (DAO).
- [PostgreSQL](https://www.postgresql.org/) - PostgreSQL is a powerful, open-source object-relational database system that uses and extends the SQL language combined with many features that safely store and scale the most complicated data workloads. 
- [Kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime) - A multiplatform Kotlin library for working with date and time.
- [Bcrypt](https://github.com/patrickfav/bcrypt) - A Java standalone implementation of the bcrypt password hash function. Based on the Blowfish cipher it is the default password hash algorithm for OpenBSD and other systems including some Linux distributions.
- [Apache Commons Email](https://github.com/apache/commons-email) - Apache Commons Email aims to provide an API for sending email. It is built on top of the JavaMail API, which it aims to simplify.
- [Ktor OpenAPI/Swagger](https://github.com/LukasForst/ktor-openapi-generator) - The Ktor OpenAPI Generator is a library to automatically generate the descriptor as you route your ktor application.
- [Valiktor](https://github.com/valiktor/valiktor) - Valiktor is a type-safe, powerful and extensible fluent DSL to validate objects in Kotlin

## Requirements

- [JAVA 11](https://jdk.java.net/11/) (or latest)
- [PostgreSQL](https://www.postgresql.org/) (latest)

## How to run

- `git clone git@github.com:piashcse/ktor-E-Commerce.git` 
-  `Create a db in postgreSQL`
-  Replace your db name in `dataSource.databaseName=ktor-1.0.0` instead of ktor-1.0.0 in the hikari.properties from the resource folder 
- `run fun main()` from application class

## Detail Api Documentation

<details>
  
<summary> <h4> <code> GET /login</h4></code></summary>

### Curl

    curl -X 'GET' \ 'http://localhost:8080/login?email=piash599%40gmail.com&password=p1234&userType=user' \ 
    -H 'accept: application/json'

### Request URL

    http://localhost:8080/login?email=piash599%40gmail.com&password=p1234&userType=user


### Response
```
{
  "isSuccess": true,
  "statsCode": {
    "value": 200,
    "description": "OK"
  },
  "data": {
    "user": {
      "id": "a9a662a7-50fe-4f13-8eab-0e0810fb9909",
      "email": "piash599@gmail.com",
      "userType": {
        "id": "04d85ebe-a667-4f80-94c8-0f68a3b3d96d",
        "userType": "user"
      }
    },
    "accessToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6Imt0b3IuaW8iLCJlbWFpbCI6InBpYXNoNTk5QGdtYWlsLmNvbSIsInVzZXJJZCI6ImE5YTY2MmE3LTUwZmUtNGYxMy04ZWFiLTBlMDgxMGZiOTkwOSIsInVzZXJUeXBlIjoidXNlciIsImV4cCI6MTY5Mjc2NTM0MX0.8GscAPCxFWOhN2bmy5bsoz5V311O4g72XqlEUWoz_y0wADkTzdgOVfG5CKJba5VUvwNiVE3MmQPmNt-fq6hyyw"
  }
}
```   
</details>

<details>
  
<summary> <h4> <code> POST /registration</h4></code></summary>

### Curl

```
  curl -X 'POST' \
  'http://localhost:8080/registration' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "email": "piash88@gmail.com",
  "password": "piash956",
  "userType": "admin"
}'
``` 

### Request URL

    http://localhost:8080/registration


### Response
```
{
  "isSuccess": true,
  "statsCode": {
    "value": 200,
    "description": "OK"
  },
  "data": {
    "email": "piash88@gmail.com"
  }
}

```   
</details>


<details>
  
<summary> <h4> <code> GET /forget-password</h4></code></summary>

### Curl

```
 curl -X 'GET' \
  'http://localhost:8080/forget-password?email=piash599%40gmail.com' \
  -H 'accept: application/json'
``` 

### Request URL

```
[ curl -X 'GET' \
  'http://localhost:8080/forget-password?email=piash599%40gmail.com' \
  -H 'accept: application/json'](http://localhost:8080/forget-password?email=piash599%40gmail.com
)
``` 


### Response
```
{
  "isSuccess": true,
  "statsCode": {
    "value": 200,
    "description": "OK"
  },
  "data": "verification code send to piash599@gmail.com"
}

```   
</details>

<details>
  
<summary> <h4> <code> GET /verify-change-password</h4></code></summary>

### Curl

```
curl -X 'GET' \
  'http://localhost:8080/verify-change-password?email=piash599%40gmail.com&verificationCode=9889&password=p1234' \
  -H 'accept: application/json'
``` 

### Request URL

```
[curl -X 'GET' \
  'http://localhost:8080/verify-change-password?email=piash599%40gmail.com&verificationCode=9889&password=p1234' \
  -H 'accept: application/json'](http://localhost:8080/verify-change-password?email=piash599%40gmail.com&verificationCode=9889&password=p1234
)
``` 


### Response
```
{
  "isSuccess": true,
  "statsCode": {
    "value": 200,
    "description": "OK"
  },
  "data": "Password change successful"
}

```   
</details>



## 👨 Developed By

<a href="https://twitter.com/piashcse" target="_blank">
  <img src="https://avatars.githubusercontent.com/piashcse" width="80" align="left">
</a>

**Mehedi Hassan Piash**

[![Twitter](https://img.shields.io/badge/-twitter-grey?logo=twitter)](https://twitter.com/piashcse)
[![Web](https://img.shields.io/badge/-web-grey?logo=appveyor)](https://piashcse.github.io/)
[![Medium](https://img.shields.io/badge/-medium-grey?logo=medium)](https://medium.com/@piashcse)
[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/piashcse/)

# License
```
Copyright 2023 piashcse (Mehedi Hassan Piash)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
