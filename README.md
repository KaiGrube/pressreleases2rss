# Project: pressreleases2rss

## 1. Description

A function that is web scraping the press release of the senate of Hamburg and converting them into a RSS feed.
Provides a handler function to deploy it as a serverless lambda function at AWS. 

"I wrote this function only for myself when corona crises started and wanted to receive the latest information on my feed-reader."

## 2. Tech Stack
+ Java 1.8
+ JSoup (web scraping)
+ Apache Commons (escaping)
+ AWS Lambda (serverless handler function)
+ Java Standard Libraries (date, nio)
+ Gradle

## 3. Notes
+ For local testing run 'LocalStarter'

## 4. Todo
+ Deploy via Cloudfront instead of using AWS-CLI and Bash
+ Check if abstract Client class is till needed (cache functionality?) 