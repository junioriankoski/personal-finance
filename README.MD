# Personal Finance API

REST API for personal finance management built with Java and Spring Boot, featuring JWT authentication and per-user data filtering.

## Tech Stack
- Java 21
- Spring Boot 4.1.0
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Maven

## Architecture
The project follows a layered structure:
- **Controller** — handles HTTP requests and responses
- **Service** — contains business logic
- **Repository** — handles database access
- **DTOs** (Request/Response) — control what data enters and leaves the API

## Features
- JWT authentication (register and login)
- Each user sees only their own data
- Full CRUD for transactions and categories
- Balance calculation in real time
- Spending summary by category
- Filter transactions by date range
- Input validation with proper error messages

## Endpoints

### Auth
- `POST /auth/register` — register a new user
- `POST /auth/login` — login and receive JWT token

### Transactions (`/transacoes`)
- `GET /transacoes` — list all transactions (authenticated user only)
- `GET /transacoes/{id}` — get a transaction by id
- `GET /transacoes/saldo` — get current balance
- `GET /transacoes/categorias` — spending summary by category
- `GET /transacoes/periodo?inicio=&fim=` — filter by date range
- `POST /transacoes` — create a transaction
- `PUT /transacoes/{id}` — update a transaction
- `DELETE /transacoes/{id}` — delete a transaction

### Categories (`/categorias`)
- `GET /categorias` — list all categories
- `GET /categorias/{id}` — get a category by id
- `POST /categorias` — create a category
- `PUT /categorias/{id}` — update a category
- `DELETE /categorias/{id}` — delete a category

## How to Run

```bash
./mvnw spring-boot:run
```

`Set the following environment variables:`

DATABASE_URL=jdbc:postgresql://host/dbname
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password

Or create `src/main/resources/application-local.properties` for local development.

## Author
Junior Iankoski — [GitHub](https://github.com/junioriankoski)

## Status
🚧 Work in progress — frontend in React available at [personal-finance-frontend](https://github.com/junioriankoski/personal-finance-frontend)