## Crud operations using Grpc services with the Rest Wrapper.
#### -> Basic Five api's created CreateBook,GetBook,GetAllBooks,UpdateBook and DeleteBook.
#### -> Also implemented Jwt Authentication and Role based Authorization 
#### -> implemented Profiles local and prod , in prod data is stored in persist h2 database using file mechanism and in local profile data is stored in inmemory h2 database .
#### -> In prod profile user need to pass bearer token for authentication and in local profile no need to pass token.
#### -> Grpc client also created for making rest wrapper on top of grpc service.
#### -> Rest api's also documented using swagger .
#### -> Mock client also created for local profile.
#### -> Grpc server is running on port 9090
#### -> Rest server is running on port 8080 
#### -> Grpc transcoding to http/Json is implemented using envoy filter.
#### -> Basic ui frontend also created for performing all api's operation and it's running on port 4500.
#### -> Exception handling also done in both Grpc Services and rest api's.
#### -> Springboot actuator also implemented to monitor the health of application.
#### -> java 17 is used 

## For installation 
#### windows 10 above needed.
#### jdk 17 present in sytem.
#### clone the project by running this command : git clone https://github.com/SukritanGupta/GrpcProject.git
#### Postman
#### github 

## Prequiste 
#### Should know about java 17, functional programming , SpringBoot , Angular 17 , Spring security , grpc , protobufs etc
#### Should know about spring data jpa and basic sql queries.
#### Should know about git.

## Authentication Controllers
#### Create User: Post request
##### url : http://localhost:8081/register 
##### example payload  : 
{
    "username":"Sukritan1",
    "password":"aman",
    "role":"USER"
  
}
#### Get User/login: 
##### url : http://localhost:8081/login
##### example payload: 
{
   "userName":"Amu",
    "password":"aman"
}
##### Copy the token and used in grpc services and rest api's for authorization.

## Rest Api's 
#### Need to pass Bearer token in Authorization header , also Content-Type and Accept application/json in headers.
#### Create Book Api:  Post call
##### url : http://localhost:8081/createBookDetails 
##### for example in requestBody pass: 
#####  
{
    "bookId":7000,
    "name":"Ansii C programming",
    "authorName":"Sukritan gupta",
    "price":9800
}
#### Get Book Api : Get call
##### url : http://localhost:8081/getBookDetails/{bookId} 

#### GetAllBooks Api: Get call
##### url : http://localhost:8081/getAllBooks

#### UpdateBook Api : Put call 
##### url : http://localhost:8081/updateBook/{BookId} 
##### example payload: 
{
  "name":"Ansi",
    "authorName":"Aman Mahajan",
    "price":600  
}

#### DeleteBook Api : Delete call 
##### url : http://localhost:8081/deleteBook/{bookId} 

## Grpc Services and payload: 
#### CreateBook : 
##### example payload: 
{
    "bookDetails": {
        "bookId": 3,
        "authorName": "Sukritan",
        "name": "RD sharma",
        "price": 90
    }
} 
#### Get Book : 
##### example payload: 
{
    "bookId": 1
}
#### GetAllBooks: 
##### example payload: 
{
   "sizeofPage":5 
}

#### UpdateBook :
##### example payload: 
{
     "bookDetails": {
        "bookId": 1,
        "authorName": "Sukritan_Gupta",
        "name": "Cpp programming",
        "price":500
    }
}
#### Delete book : 
##### example payload: 
{
    "id": 52900
}

## Run envoy filter : 
### Prerequiste : Docker installed in local System 
#### Create docker image : docker build -t my-envoy1 . 
#### Run docker image : docker run -d --name my-envoy-container -p 8080:8080 my-envoy1 

## Transcoding : Payload same as other service payload 
#### Create Book : Post request 
##### url : http://localhost:8080/createBook

#### GetBook : Get request 
##### url : http://localhost:8080/getBook/{bookId} 

#### GetAllBooks: Get request 
##### url : http://localhost:8080/getAllBook 

#### UpdateBook : Put request 
##### url : http://localhost:8080/updateBook/{bookId} 
##### example payload: 
{
     "bookDetails": {
        "authorName": "Sukritan_Gupta",
        "name": "Cpp programming",
        "price":500
    }
}

Copy Right By sukritan gupta 2024
