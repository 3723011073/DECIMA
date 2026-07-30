# Marketing Analytics Backend

## Project Overview

Decision-Centric Intelligent Marketing Analytics System - A comprehensive backend for analyzing customer behavior, predicting outcomes, and optimizing marketing campaigns.

## Tech Stack

- **Java 21** with Spring Boot 3.3.0
- **PostgreSQL** for data persistence
- **Spring Security** with JWT authentication
- **Spring Data JPA** with Hibernate ORM
- **Apache Commons CSV** for data parsing
- **Flyway** for database migrations

## Project Structure

```
marketing-analytics-backend/
├── src/main/java/com/marketing/analytics/
│   ├── controller/          # REST API endpoints
│   ├── service/             # Business logic
│   ├── repository/          # Data access layer
│   ├── entity/              # JPA entities
│   ├── dto/                 # Data transfer objects
│   ├── config/              # Configuration classes
│   ├── security/            # Security filters & utilities
│   ├── exception/           # Custom exceptions
│   ├── util/                # Utility classes
│   ├── preprocessing/       # Data preprocessing logic
│   ├── analytics/           # Analytics calculations
│   └── MarketingAnalyticsApplication.java
├── src/main/resources/
│   ├── application.yml      # Application configuration
│   └── db/migration/        # Database migration scripts
├── pom.xml                  # Maven configuration
└── README.md
```

## Database Setup

### Prerequisites

- PostgreSQL 12 or higher installed and running
- Create database:

```sql
CREATE DATABASE marketing_analytics_db;
```

### User Setup

```sql
-- Default user
CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE marketing_analytics_db TO postgres;
```

## Installation & Setup

### 1. Clone Repository

```bash
git clone <repository-url>
cd marketing-analytics-backend
```

### 2. Configure Database

Update `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/marketing_analytics_db
    username: postgres
    password: postgres
```

### 3. Build Project

```bash
mvn clean install
```

### 4. Run Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api/v1`

## API Documentation

### Authentication

#### Login Endpoint
```
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password"
}

Response:
{
  "success": true,
  "message": "Login successful",
  "data": {
    "userId": 1,
    "email": "user@example.com",
    "name": "User Name",
    "role": "ADMIN",
    "accessToken": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "expiresIn": 86400000
  }
}
```

### Using Access Token

Include in all requests:
```
Authorization: Bearer {accessToken}
```

## Database Entities

### Users
- Authentication and authorization
- Roles: ADMIN, MANAGER, ANALYST

### Customers
- Customer demographic information
- Core customer data

### CustomerBehavior
- Website visits, purchase history
- Engagement metrics

### CustomerSegment
- RFM analysis results
- Segment classification

### Campaign
- Campaign metadata
- Status tracking

### CampaignResponse
- Customer interactions with campaigns
- Conversion tracking

### Prediction
- ML model predictions
- Confidence and risk scores

### CampaignOutcome
- Actual campaign results
- Comparison with predictions

### ModelMetrics
- Model performance metrics
- Accuracy, precision, recall, F1, AUC

## Implementation Phases

### PHASE 1: Foundation (Complete ✓)
- [x] Project setup with Spring Boot 3
- [x] Database schema design & Flyway migration
- [x] Entity creation with JPA
- [x] Repository layer
- [x] JWT authentication
- [x] Security configuration
- [x] Exception handling
- [x] Basic DTOs

### PHASE 2: Core Services (In Progress)
- [ ] Authentication Service
- [ ] User Management Service
- [ ] Customer Service
- [ ] Campaign Service
- [ ] Prediction Service

### PHASE 3: Data Processing
- [ ] Dataset Upload Module
- [ ] CSV Parsing & Validation
- [ ] Data Preprocessing Engine
- [ ] RFM Analysis
- [ ] Feature Engineering

### PHASE 4: ML Integration
- [ ] ML Service Integration
- [ ] Prediction Engine
- [ ] Model Training Pipeline
- [ ] Model Monitoring

### PHASE 5: Analytics & Insights
- [ ] Analytics Dashboard APIs
- [ ] AI Insights Generation
- [ ] Performance Analysis
- [ ] Reporting

### PHASE 6: Testing & Deployment
- [ ] Unit Tests
- [ ] Integration Tests
- [ ] Docker containerization
- [ ] CI/CD Pipeline

## Upcoming Steps

1. **Customer Service Implementation**
   - CRUD operations
   - Bulk upload support
   - Query optimization

2. **Dataset Upload Module**
   - File validation
   - CSV parsing
   - Data cleanup

3. **RFM Analysis Engine**
   - Recency calculation
   - Frequency analysis
   - Monetary value scoring

4. **ML Service Integration**
   - REST client for ML service
   - Prediction handling
   - Error recovery

5. **Testing Suite**
   - Controller tests
   - Service layer tests
   - Database integration tests

## Configuration

### JWT Configuration

Set in `application.yml`:
```yaml
jwt:
  secret: your-secret-key-minimum-256-characters
  expiration: 86400000  # 24 hours
  refresh-expiration: 604800000  # 7 days
```

### ML Service Configuration

```yaml
ml-service:
  url: http://localhost:5000
  endpoints:
    predict: /predict
    train: /train
    evaluate: /evaluate
```

## Logging

Logs are configured in `application.yml` and saved to `logs/marketing-analytics.log`

Log Levels:
- INFO: General information
- DEBUG: Detailed debugging for com.marketing.analytics
- DEBUG: Spring Security debug

## Error Handling

All errors follow standardized format:

```json
{
  "success": false,
  "message": "Error description",
  "errorCode": "ERROR_CODE",
  "timestamp": "2024-01-01T12:00:00"
}
```

Common Error Codes:
- `RESOURCE_NOT_FOUND`: 404
- `VALIDATION_ERROR`: 400
- `AUTHENTICATION_FAILED`: 401
- `INTERNAL_SERVER_ERROR`: 500

## Development Guidelines

### Code Style
- Follow Google Java Style Guide
- Use Lombok for boilerplate reduction
- Comprehensive JavaDoc comments

### Testing
- Write tests for all business logic
- Use @DataJpaTest for repository tests
- Use @WebMvcTest for controller tests

### Commits
```
[FEATURE] Module: Description
[BUGFIX] Module: Description
[REFACTOR] Module: Description
```

## Next Phase: Step-by-Step Implementation

### Step 1: Customer Service

I'll create CustomerService with:
- Get all customers with filtering
- Get customer by ID
- Create customer from data
- Update customer information

### Step 2: Dataset Upload

Dataset upload processing with:
- File validation (CSV format check)
- Header validation
- Data type checking
- Error reporting

### Step 3: Data Preprocessing

Data cleaning and transformation:
- Handle missing values
- Normalize numerical fields
- Standardize categorical data
- Feature extraction

### Step 4: RFM Analysis

Customer segmentation:
- Recency calculation
- Frequency scoring
- Monetary value scoring
- RFM combination and segment assignment

## Support & Contact

For questions or issues:
1. Check existing documentation
2. Review error logs in `logs/marketing-analytics.log`
3. Verify database connectivity
4. Check JWT token validity

---

**Status**: Phase 1 Complete | Phase 2 In Progress
**Last Updated**: 2025-01-15
**Version**: 1.0.0-SNAPSHOT
