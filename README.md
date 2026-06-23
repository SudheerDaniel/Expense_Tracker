# Expense Tracker - Backend

A production-grade REST API for tracking personal expenses, built with Spring Boot 3.3.4 and deployed on Railway.
## Live API
`https://expensetracker-production-1bc5.up.railway.app`

## Tech Stack
- **Framework:** Spring Boot 3.3.4 (Java 17)
- **Database:** PostgreSQL (Neon serverless)
- **Storage:** AWS S3 (receipt uploads)
- **Auth:** JWT with refresh token rotation
- **ORM:** Spring Data JPA / Hibernate
- **Deployment:** Railway (Docker)

## Features
- JWT authentication with silent refresh token rotation
- Expense CRUD with category, subcategory, merchant, payment method, notes and receipt URL
- Date range filtering and summary endpoints
- Monthly budget management
- Receipt upload to AWS S3
- CORS configured for Vercel frontend
- Global exception handling

## Testing
Unit tests written with JUnit 5 + Mockito covering the service layer.
Run tests with:
```bash
mvn test
```

## CI/CD
GitHub Actions runs on every push and every PR:
- Builds the project with Maven
- Runs the full test suite
- Blocks merge to main if the build or tests fail

## Project Structure
src/main/java/com/example/springbootmvcexample/

├── config/          # S3Config, security beans

├── controller/      # ExpenseController, AuthController, S3Controller, BudgetController

├── model/           # ExpenseTracker, User, Budget

├── repository/      # JPA repositories

├── security/        # JwtFilter, JwtUtil, SecurityConfig

└── service/         # ExpenseTrackerService, UserService, S3Service
## Environment Variables
| Variable | Description |
|---|---|
| `SPRING_PROFILES_ACTIVE` | Set to `docker` for production |
| `DB_URL` | PostgreSQL connection string |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key for signing JWTs |
| `AWS_ACCESS_KEY` | AWS IAM access key |
| `AWS_SECRET_KEY` | AWS IAM secret key |
| `AWS_REGION` | AWS region (e.g. `us-east-1`) |
| `AWS_BUCKET_NAME` | S3 bucket name |

## Running Locally
**Prerequisites:** Docker Desktop, Java 17, Maven

```bash
# Clone the repo
git clone https://github.com/SudheerDaniel/Expense_Tracker.git
cd Expense_Tracker

# Build the jar
mvn clean package -DskipTests
# Start with Docker Compose (PostgreSQL + MinIO + app)
docker-compose up --build
```

API available at `http://localhost:8080`

## API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/signup` | Register a new user |
| POST | `/api/auth/login` | Login and receive tokens |
| POST | `/api/auth/refresh` | Refresh access token |
| POST | `/api/auth/logout` | Invalidate refresh token |

### Expenses
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/expenses` | Get all expenses |
| POST | `/api/expenses` | Add a new expense |
| PUT | `/api/expenses/{id}` | Update an expense |
| DELETE | `/api/expenses/{id}` | Delete an expense |
| GET | `/api/expenses/summary` | Get spending summary by date range |

### Budget
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/budget` | Get budget for a month |
| PUT | `/api/budget` | Set or update a budget |

### S3
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/s3/upload` | Upload a receipt |
| GET | `/api/s3/presigned-url` | Get a presigned URL to view a receipt |



## Frontend
The React frontend is at [Expense-Tracker-Frontend](https://github.com/SudheerDaniel/Expense_Tracker_Frontend)

## License
MIT
