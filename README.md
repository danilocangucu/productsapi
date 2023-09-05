# Spring Boot + MongoDB Products API

This is a project that builds a RESTful API using Spring Boot and MongoDB.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/javase-downloads.html) or higher installed
- [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) (more details in [MongoDB Settings](#mongodb-settings))
- [Gradle](https://gradle.org/) build tool installed (for building the project)

## Getting Started

1. Clone this repository:

   ```shell
   git clone https://github.com/danilocangucu/productsapi.git

2. Change to the project directory:

   ```shell
   cd productsapi
   ```

3. Configure MongoDB connection in `src/main/resources/application.properties`:

   ```properties
   spring.data.mongodb.uri=mongodb://<username>:<password>@<host>/<database>?retryWrites=true&w=majority
   spring.data.mongodb.database=<database>
   ```

4. Build and run the application:

   ```shell
   ./gradlew bootRun
   ```

5. The application will be accessible at [http://localhost:8080](http://localhost:8080).

## API Endpoints

- **GET /products**: Retrieve a list of products. The response excludes the "userId" field.

## MongoDB Settings

You should have the following three documents inside collection named "products" on your MongoDB Atlas database:

1. Document 1:
   ```json
   {"_id":{"$oid":"64f714f0c468d71fbc8e0bef"},"name":"Lamp","description":"Contemporary lamp","price":{"$numberDouble":"9.99"},"userId":"123"}
   ```

2. Document 2:
   ```json
   {"_id":{"$oid":"64f715bcc468d71fbc8e0bf0"},"name":"Coffee Table","description":"Modern glass coffee table with chrome accents","price":{"$numberDouble":"149.99"},"userId":"456"}
   ```

3. Document 3:
   ```json
   {"_id":{"$oid":"64f71661c468d71fbc8e0bf1"},"name":"Smartphone","description":"High-end smartphone with cutting-edge features","price":{"$numberDouble":"699.99"},"userId":"789"}
   ```

## Running Tests

To run tests for this project, follow these steps:

1. In one terminal window, run the application as shown previously.

2. Then, open another terminal window and navigate to the project directory:

   ```shell
   cd productsapi
   ```

3. Run the following command to execute tests:

   ```shell
   ./gradlew test
   ```

   This will run the tests and provide test results in the terminal.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

You can copy and paste this updated template into your README.md file on GitHub. Don't forget to replace `<username>`, `<password>`, `<host>`, and `<database>` in the MongoDB connection string with your actual MongoDB credentials and database information.