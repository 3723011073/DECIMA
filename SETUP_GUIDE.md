# Quick Setup Guide

## Prerequisites Checklist

- [ ] Java 21 installed (`java -version`)
- [ ] Maven installed (`mvn -version`)
- [ ] PostgreSQL installed and running
- [ ] Git (for version control)

## Step 1: Database Preparation (5 minutes)

### On PostgreSQL (Windows/Mac/Linux)

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE marketing_analytics_db;

# Create user (if needed)
CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE marketing_analytics_db TO postgres;

# Verify
\l  # List databases
\q  # Exit
```

### Connection String Reference
```
Database: marketing_analytics_db
User: postgres
Password: postgres
Host: localhost
Port: 5432
```

## Step 2: Project Setup (2 minutes)

```bash
# Navigate to project
cd marketing-analytics-backend

# Build project (downloads dependencies - ~5 min first time)
mvn clean install

# Run application
mvn spring-boot:run
```

## Step 3: Verify Setup (2 minutes)

### Test Health Endpoint

```bash
curl http://localhost:8080/api/v1/health
```

Expected response:
```json
{
  "success": true,
  "message": "Service is healthy",
  "data": "OK"
}
```

## Step 4: Create First User

Use PostgreSQL to insert initial user:

```sql
INSERT INTO users (email, name, password, role, is_active, created_at, updated_at) 
VALUES (
    'admin@test.com', 
    'Admin User', 
    '$2a$10$...', -- Use bcrypt password
    'ADMIN', 
    true, 
    NOW(), 
    NOW()
);
```

**Note**: Password needs to be bcrypted. Use this Java snippet to generate:

```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        String password = "password123";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode(password);
        System.out.println(encoded);
    }
}
```

Or use online tool (not recommended for production): https://bcrypt-generator.com/

## Step 5: Login Test

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@test.com",
    "password": "password123"
  }'
```

Expected response:
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "userId": 1,
    "email": "admin@test.com",
    "name": "Admin User",
    "role": "ADMIN",
    "accessToken": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "expiresIn": 86400000
  }
}
```

## Common Issues & Solutions

### Issue: Maven downloads stuck
**Solution**: First build takes time. Add `-X` for debug info: `mvn -X clean install`

### Issue: "Cannot connect to database"
**Solution**: Verify PostgreSQL is running
```bash
# Windows
Get-Process postgres

# Mac
ps aux | grep postgres

# Linux
systemctl status postgresql
```

### Issue: Port 8080 already in use
**Solution**: Change in `application.yml`:
```yaml
server:
  port: 8081
```

### Issue: Database migration failed
**Solution**: Check Flyway migrations in `src/main/resources/db/migration/`

### Issue: JWT secret too short
**Solution**: Generate longer key in `application.yml`:
```
jwt.secret: aVeryLongSecretKeyThatIsAtLeast256CharactersLongForJWTSigningPurposesOnlyChangeThis
```

## IDE Setup (Optional but Recommended)

### Visual Studio Code
Install extensions:
- Extension Pack for Java (Microsoft)
- Spring Boot Extension Pack
- REST Client

### IntelliJ IDEA
- Spring Boot support built-in
- Create run configuration for `spring-boot:run`

## Project File Structure Reference

```
marketing-analytics-backend/
├── pom.xml                          # Maven config (✓)
├── README.md                        # Documentation (✓)
├── SETUP_GUIDE.md                   # This file
├── src/main/java/com/marketing/analytics/
│   ├── MarketingAnalyticsApplication.java  # Entry point (✓)
│   ├── controller/
│   │   └── AuthController.java      # Auth endpoints (✓)
│   ├── service/
│   │   └── UserService.java         # Auth logic (✓)
│   ├── repository/                  # Data layer (✓ All)
│   ├── entity/                      # Models (✓ All)
│   ├── dto/                         # Transfer objects (✓ Some)
│   ├── config/
│   │   ├── SecurityConfig.java      # Security (✓)
│   │   └── CustomUserDetailsService.java (✓)
│   ├── security/
│   │   ├── JwtUtil.java            # Token utils (✓)
│   │   └── JwtAuthenticationFilter.java (✓)
│   └── exception/
│       ├── ResourceNotFoundException.java (✓)
│       └── GlobalExceptionHandler.java (✓)
├── src/main/resources/
│   ├── application.yml              # Config (✓)
│   └── db/migration/
│       └── V1__Initial_Schema.sql   # Schema (✓)
└── src/test/                        # Tests (TODO)
```

✓ = Implemented | TODO = Next Phase

## What's Included (Phase 1)

- [x] Spring Boot 3 configured
- [x] PostgreSQL integrated
- [x] All 10 database tables
- [x] JPA entities with relationships
- [x] Repository layer
- [x] JWT authentication
- [x] Role-based security
- [x] Exception handling
- [x] DTOs for API
- [x] Database migrations

## What's Next (Phase 2)

- [ ] Customer Service CRUD
- [ ] Dataset Upload Module
- [ ] CSV Parser
- [ ] Data Preprocessing
- [ ] RFM Analysis
- [ ] ML Integration
- [ ] Campaign Management
- [ ] Prediction Engine
- [ ] Analytics Dashboard APIs
- [ ] Unit & Integration Tests

## Useful Commands

```bash
# View logs
tail -f logs/marketing-analytics.log

# Run tests
mvn test

# Build for production
mvn clean package

# Check Java version
java -version

# Connect to database
psql -U postgres -d marketing_analytics_db

# List tables in database
\dt

# Exit psql
\q
```

## Configuration Files Location

- **Application properties**: `src/main/resources/application.yml`
- **Database migrations**: `src/main/resources/db/migration/`
- **Log file**: `logs/marketing-analytics.log`

## Next Phase: Phase 2 Steps

When ready to proceed:

1. Run `mvn spring-boot:run` and verify it starts
2. Proceed to implement Customer Service
3. Add Dataset Upload Module
4. Build CSV Parser
5. Implement Data Preprocessing

---

**Estimated Setup Time**: 15-20 minutes (first time includes dependency downloads)
**Current Status**: Ready for Phase 2 Implementation
