# Getting Started

## Code Practice

- For DTO:
    - "Req" for request (input) classes
    - "Res" for response (output) classes

## Compile 
Use mvnw (maven wrapper) 
- to clean: .\mvnw clean
- to install: .\mvnw install

## Test
To Test we can use Swagger or PostMan
- on localhost: http://localhost:8080/sales/swagger-ui/index.html
- or using curl:
```
curl -X 'POST' \
'http://localhost:8080/sales/v1/invoice' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '1 imported bottle of perfume at 27.9999
1 bottle of perfume at 18.99
1 packet of headache pills at 9.75
1 box of imported chocolates at 11.25'
```

## Reference Documentation
