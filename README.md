# Spring + MongoDB Shop API

This is a project that builds a RESTful API using Spring Boot and MongoDB.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 17 or higher installed
- Gradle build tool installed (for building the project)
- [Postman](https://www.postman.com/) (recommended for testing the API)

## Getting Started

1. Clone this repository:

   ```
   git clone https://github.com/danilocangucu/spring-mongodb-atlas-shop-api.git
   ```

2. Change to the project directory:

   ```
   cd spring-mongodb-atlas-shop-api
   ```

3. Build and run the application:

   ```
   ./gradlew bootRun
   ```

   The application will be accessible at https://localhost:443.

## API Endpoints

### Products Controller Endpoints:

- **GET /public/products**: Retrieve a list of products. The response excludes the "userId" field.
- **POST /private/products**: Create a new product.
- **GET /private/products/{requestedId}**: Retrieve a product by ID.
- **PUT /private/products/{requestedId}**: Update a product by ID.
- **DELETE /private/products/{id}**: Delete a product by ID.
- **GET /private/products**: Retrieve a list of products from the current user.

### Users Controller Endpoints:

- **GET /private/users**: Retrieve the current user's information.
- **GET /private/admin/users**: Retrieve a list of all users.
- **PUT /private/users**: Update the current user's information.
- **DELETE /private/users**: Delete the current user's account.
- **DELETE /private/admin/users/{id}**: Delete a user by ID (Admin Only).

### Authenticate Controller Endpoints:

- **POST /public/register**: Register a new user.
- **POST /public/authenticate**: Authenticate an existing user.

Please note that the endpoints under `/private` require authentication, and the endpoints under `/admin` require admin role authorization.

## Obtaining Product IDs with Postman

In this project, product IDs are automatically generated when you create a new product. To find the product ID for a specific product using Postman, follow these steps:

1. **Create a New Product**:

   - Open Postman and create a new POST request.
   - Set the request URL to `https://localhost:443/private/products`.
   - In the Headers section, add a key `Authorization` with a value of `Bearer <your-jwt-token>` to authenticate the request. Replace `<your-jwt-token>` with your actual JWT token.
   - In the Body section, select `raw` and enter the JSON data for the new product:

     ```json
     {
         "name": "New Product",
         "description": "Description of the new product",
         "price": 19.99
     }
     ```

   - Send the request. Upon successful creation, the API will respond with a `201 Created` status code, and the `Location` header of the response will contain the URL of the newly created product, including its unique ID.

2. **Retrieve a List of Products**:

   - Open Postman and create a new GET request.
   - Set the request URL to `https://localhost:443/public/products`.
   - Send the request. The response will include product details, including their IDs.

3. **Use the Product ID**:

   - Once you have obtained the product ID from the creation request or the list of products, you can use it in subsequent requests to interact with the specific product, such as updating or deleting it.

## User Registration with Postman

To register a new user using Postman, follow these steps:

1. **Register a New User**:

   - Open Postman and create a new POST request.
   - Set the request URL to `https://localhost:443/public/register`.
   - In the Body section, select `raw` and enter the JSON data for the new user:

     ```json
     {
         "name": "John Doe",
         "email": "johndoe@example.com",
         "password": "securepassword"
     }
     ```

   - Send the request. Upon successful registration, the API will respond with a confirmation message.

## User Authentication with Postman

To authenticate an existing user using Postman, follow these steps:

1. **Authenticate User**:

   - Open Postman and create a new POST request.
   - Set the request URL to `https://localhost:443/public/authenticate`.
   - In the Body section, select `raw` and enter the JSON data for the user's credentials:

     ```json
     {
         "email": "johndoe@example.com",
         "password": "securepassword"
     }
     ```

   - Send the request. Upon successful authentication, the API will respond with a valid JWT token, which can be used for accessing protected endpoints.

## Available Users from the Database

- User:
  - Username: user123
  - Password: user123

- Admin:
  - Username: admin123
  - Password: admin123
