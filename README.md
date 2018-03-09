# funds

Investment Funds Division Service

# branch

develop

# run

./mvnw spring-boot:run

warning: populate H2 database, one can use datasets from FundsApplicationClientTests 

# api

/funds/{strategy}/{value}

- strategy: safe, balanced, agressive
- value: to be divided between user defined funds based on user defined strategies

example:
/funds/safe/10000

# test

FundsApplicationClientTests

- contains tests with initialization datasets of strategies and funds
- assertions are located in safe*.json files 

# remarks

Due to time constraints the codebase might not be 100% bug free. Feel free to let me know.
