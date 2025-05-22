# Customer Reward Points

This project aims to calculate reward points earned by the customer for specified number of Months. Reward points is calculated based on the transaction amount. *Customer recieves 1 reward point if transaction amount more than 50. customer recieves 2 reward points if amount is more than 100*. This project aims in calculating reward points gained for past *n* months. Where *n* is number of months specified in request. 
For e.g. Purchase amount is 102 , reward points is (50 × 1 + 2 × 2) 54 

## API functionalities
This project has controller 3 apis which facilitates saving transaction, get details of transaction performed and calculate reward points for the customer’s name passed.

To store transaction details and perform calculation h2 database is used connection details are mentioned in application.Properties file.

This Project uses 3 apis. Let’s go over one by one 

**Api/v1/getTransactions**: This api will provide details all transactions of particular customer stored in database.
**Api/v1/saveTransaction**: This api will store transaction details into database.
**Api/v1/getRewardPoints**: This api calculates the reward points gained for a particular customer. 
Note: This api calculates all transactions performed last 30 days as previous months reward points.  


**H2 console that can used to check transaction records url**  http://localhost:8080/h2console/

Sample input and output examples
### Save transaction details 
request:
```
post http://localhost:8080/api/v1/saveTransaction
```
body
```
 {
    "userName": "JOHN",
    "transactionDate": "2025-04-15",
    "amount": 103
    }
```
Response:
```
Transaction added succssfully
```
if amount is 0 / negative exception thrown
```
 {
    "userName": "JOHN",
    "transactionDate": "2025-04-17",
    "amount": 0
    }
```
Response:
```
Amount should not be 0/Negative value
```
### Get all transaction details
Request:
```
http://localhost:8080/api/v1/getTransactions?customerName=JOHN
```
Response: 
```
[
    {
        "id": 1,
        "username": "JOHN",
        "transactionDate": "2025-04-15",
        "amount": 103.0
    },
    {
        "id": 2,
        "username": "JOHN",
        "transactionDate": "2025-04-17",
        "amount": 120.0
    }
]
```
Request: Error message is diaplayed as below if customer name is not given.
```
http://localhost:8080/api/v1/getTransactions?customerName=
```
Response: 
```
user/customer name should not be empty
```
### Calculate Reward points 
Request: 
```
GET http://localhost:8080/api/v1/getRewardPoints
```
requestBody:
```
{
    "customerName":"JOHN",
    "noOfMonths":3
}
```
Response:
```
{
    "totalRewardPoints": 146,
    "customerName": "JOHN",
    "monthlyRewards": {
        "MARCH": 0,
        "FEBRUARY": 0,
        "APRIL": 146
    }
}
```

Request: Error message is diaplayed as below if number of months is zero.
```
GET http://localhost:8080/api/v1/getRewardPoints
```
requestBody:
```
{
    "customerName":"JOHN",
    "noOfMonths":0
}
```
Response:
```
Month should not be 0/Negative value
```