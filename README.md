# Project: pressreleases2rss

## 1. Description

A function that is web scraping the press release of the senate of Hamburg and converting them into a RSS feed.
Provides a handler function to deploy it as a serverless lambda function at AWS. 

## 2. Tech Stack
+ Java 11
+ JSoup (web scraping)
+ JAXB (map classes to xml)
+ Apache Commons (hash function)
+ AWS Lambda (serverless handler function)
+ Java Standard Libraries (date, nio)
+ Maven

## 3. Notes
+ For local testing run 'LocalStarter'

## 4. todo
+ Null checks
+ Logging