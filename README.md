## Crud operations using Grpc services with the Rest Wrapper.
##### Basic Five api's created CreateBook,GetBook,GetAllBooks,UpdateBook and DeleteBook.
##### Also implemented Jwt Authentication and Role based Authorization 
# implemented Profiles local and prod , in prod data is stored in persist h2 database using file mechanism and in local profile data is stored in inmemory h2 database .
# In prod profile user need to pass bearer token for authentication and in local profile no need to pass token.
# Grpc client also created for making rest wrapper on top of grpc service.
# Rest api's also documented using swagger .
# Mock client also created for local profile.
# Grpc server is running on port 9090
# Rest server is running on port 8080 
# Grpc transcoding to http/Json is implemented using envoy filter.
# Basic ui frontend also created for performing all api's operation .
# Exception handling also done in both Grpc Services and rest api's.
# Springboot actuator also implemented to monitor the health of application.
# java 17 is used 
