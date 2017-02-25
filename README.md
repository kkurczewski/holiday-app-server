
# Holiday app server

Server part of client-server project with Android app. Server has functionalities to improve network and CPU usage of mobile clients, like compression and custom image sizes.

## Client project

Project tied with [kkurczewski/holiday-app-android](https://github.com/kkurczewski/holiday-app-android).

## Functionalities

Server functionalities:
* panel for adding and manage holiday offers
* access to resources wrapped in JSON response
* compression of JSON response using GZIP
* images with custom sizes, for example: _.../my_image_01.jpg**?w=100&h=200**_
* push requests to Android devices (using Firebase Cloud Messaging platform)

## Technologies

Used technologies:
* SpringBoot
* MySql database

## End note

Project may not work/compile due the fact of cut off sensitive data like custom keys, private urls, etc. It's supposed to be more like read-only portfolio.
