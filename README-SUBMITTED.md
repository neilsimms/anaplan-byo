# Interview Test - Submitted Project

## Setup

To build and run the software:

```bash
./build.sh
docker-compose up --force-recreate
```

## Changes Made

### 01-initial-db.sql

The 2 data columns have been renamed and a title column has been added.

## Testing

```bash
# Smoke tests
./smoke_tests.sh

# List all data
curl 'http://localhost/dashboards' | jq .

# Get single record
curl 'http://localhost/dashboards/2' | jq .

# Add a new record
curl --request POST 'http://localhost/dashboards' \
--header 'content-type: application/json' \
--data-raw '{
  "title": "New Title"
}' | jq .

# Update the record
curl --location --request PUT 'http://localhost/dashboards/6' \
--header 'content-type: application/json' \
--data-raw '{
  "title": "New Title - updated"
}' | jq .

# Delete the record
curl --location --request DELETE 'http://localhost/dashboards/6'

## Viewing data

```
docker run -it --network bring-your-own-interview_default --rm mysql:5 mysql -hbring-your-own-interview_mysql-db_1 -uroot -ptest123
use definitions
select * from dashboards;
```