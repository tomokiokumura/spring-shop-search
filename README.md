Spring Shop Search
====

## Usage

### clone repository
```
> git clone http://github.com/mosmos21/spring-shop-search.git
```

### database setting  

Before build, you should setting information for project.  
The setting file is `src/main/resource/application.properties`.  
And, you need to create database named 'shop_search' before starting application.

#### Setting items.  

  | | key | default parameter |
  |---|---|---|
  | database url | spring.datasource.url | jdbc:postgresql://localhost:5432/shop_search |
  | database user name | spring.datasource.username | postgres |
  | database user password | spring.datasource.password | postgres |
  
 ### build
 ```
 > mvn build
 ```
 
 ### Run application
 ```
 > java -jar spring-shop-search-1.0.jar
 ```