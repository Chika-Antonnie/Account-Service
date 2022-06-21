# Account-Service
This is part of a microservice based demo banking application. It consists of 2 microservices:
1. account-service
2. transaction-service

## Initialization
Upon startup, the account-service is loaded with 3 customer records having customerId: 1001, 1002 and 1003


## Running the services
1. Clone the account-service repo main branch from: https://github.com/Chika-Antonnie/Account-Service.git
2. Clone the transaction-service repo main branch from: https://github.com/Chika-Antonnie/Transaction-Service
3. Build the two services using maven command: mvn clean package.
4. Run the account-service jar file with Java command: java -jar target/account-service.jar
5. Run the transaction-service jar file with Java command: java -jar target/transaction-service.jar

## Testing the APIS
1. Create account API: http://localhost:8089/api/v1/customer/create-account
    - Request method: PUT
    - Sample Request Body: {"customerId": 1002, "initialCredit": 200}
2. Get account API: http://localhost:8089/api/v1/customer/{accountNumber}
    -   Request method: GET
   
## Accessing the UI
-  Open http://localhost:8089/account/create on a browser
-  Enter 1001, 1002 or 1003 in "Customer ID" failed
-  Enter 0 or any number value in "Initial balance" field and click submit

## To Do
-  Implement input validation on API calls
-  Write unit tests
-  Add authentication to APIs
-  Containerize the services