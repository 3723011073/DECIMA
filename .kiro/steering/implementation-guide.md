---
inclusion: manual
---

# Implementation Guide - Decision-Centric Marketing Analytics

## Current Status: PHASE 1 COMPLETE

Phase 1 foundation is now ready. Next steps proceed through Phase 2 systematically.

## PHASE 2: Core Services Implementation

### Step 1: Complete Customer Service

**Location**: `src/main/java/com/marketing/analytics/service/CustomerService.java`

```java
@Service
@Transactional
public class CustomerService {
    - getAllCustomers() - paginated list
    - getCustomerById(id) - fetch single
    - createCustomer(dto) - insert new
    - updateCustomer(id, dto) - modify existing
    - deleteCustomer(id) - remove customer
    - searchCustomers(filters) - filtered search
    - getCustomerWithBehavior(id) - joined query
}
```

**Endpoints to create**:
- GET /api/v1/customers - List all
- GET /api/v1/customers/{id} - Get details
- POST /api/v1/customers - Create
- PUT /api/v1/customers/{id} - Update
- DELETE /api/v1/customers/{id} - Delete

### Step 2: Dataset Upload Module

**Location**: `src/main/java/com/marketing/analytics/service/DatasetUploadService.java`

Responsibilities:
1. Handle file upload
2. Validate CSV format
3. Parse headers
4. Store in database
5. Update status
6. Error handling

**Key methods**:
- uploadDataset(file, userId)
- validateCSVStructure()
- parseCSVHeaders()
- storeDatasetMetadata()

### Step 3: Data Preprocessing Pipeline

**Location**: `src/main/java/com/marketing/analytics/preprocessing/`

Components:
- `DataPreprocessor` - orchestrator
- `MissingValueHandler` - handle nulls
- `DataNormalizer` - scale numerical
- `OutlierDetector` - identify anomalies
- `FeatureExtractor` - create new features

### Step 4: RFM Analysis Engine

**Location**: `src/main/java/com/marketing/analytics/service/RFMAnalysisService.java`

Calculation logic:
1. **Recency**: Days since last purchase
2. **Frequency**: Total number of purchases
3. **Monetary**: Total amount spent

Scoring: 1-5 scale per metric
Segmentation: Combine scores into segments

Segments:
- Champions: 5-5-5, 5-5-4, 5-4-5, 4-5-5
- Loyal: 5-5-3, 5-4-4, 4-5-4, 4-4-5
- At Risk: 3-2-5, 2-3-5
- Lost: 1-1-1, 1-2-1, 2-1-1

## PHASE 3: ML Integration

### Python ML Service Architecture

**Required Endpoints** (Python Flask/FastAPI service):

1. POST /predict
   - Input: customer features
   - Output: probability, confidence

2. POST /train
   - Input: training dataset
   - Output: model metrics

3. POST /evaluate
   - Input: test dataset
   - Output: detailed metrics

4. POST /drift-detection
   - Input: recent predictions vs old
   - Output: drift indicator

### Java Service Integration

**Location**: `src/main/java/com/marketing/analytics/service/MLServiceClient.java`

Uses WebClient to call Python service:
```java
- callPredictionService(customerFeatures)
- callTrainingService(dataset)
- callEvaluationService(testData)
- handleMLServiceErrors()
```

## PHASE 4: Analytics & Insights

### Campaign Analytics Service

**Responsibilities**:
- Calculate conversion rates
- Analyze ROI
- Compare predictions vs actuals
- Generate trend analysis

### Decision Engine

**Location**: `src/main/java/com/marketing/analytics/service/DecisionEngineService.java`

Logic:
1. Get prediction
2. Get historical performance
3. Get similar customer segment patterns
4. Generate recommendation
5. Assign confidence score

## Database Indexing Strategy

Already created basic indexes. Add later:
```sql
CREATE INDEX idx_campaign_outcomes_outcome_date ON campaign_outcomes(outcome_date);
CREATE INDEX idx_predictions_model_version ON predictions(model_version);
CREATE INDEX idx_customer_behavior_total_spending ON customer_behavior(total_spending);
```

## Error Handling Strategy

All services throw ResourceNotFoundException or custom exceptions that are caught by GlobalExceptionHandler.

Pattern:
```java
try {
    // business logic
} catch (EntityNotFoundException e) {
    throw new ResourceNotFoundException("message");
}
```

## Testing Approach

### Unit Tests
- Service layer: Mock repositories
- Utility functions: Direct tests

### Integration Tests
- @DataJpaTest for repositories
- @WebMvcTest for controllers
- @SpringBootTest for full context

### Test Database
- Use embedded H2 for speed
- Or dedicated test PostgreSQL

## Security Considerations

1. **Authentication**: JWT tokens (✓ Complete)
2. **Authorization**: Role-based via @Secured
3. **Input Validation**: @Valid on DTOs (✓)
4. **SQL Injection**: Using JPA prevents this (✓)
5. **CSRF**: Disabled for REST API (✓)

## Performance Optimization

1. **Pagination**: Implement Page<T> for large datasets
2. **Lazy Loading**: Use @Lazy on collections
3. **Query Optimization**: Custom @Query when needed
4. **Caching**: @Cacheable for frequently accessed data
5. **Batch Processing**: Process large datasets in chunks

## Deployment Checklist

- [ ] All tests passing
- [ ] SonarQube scan complete
- [ ] Docker image built
- [ ] Environment variables configured
- [ ] Database migrations tested
- [ ] Load testing performed
- [ ] Security audit passed
- [ ] Documentation updated

## Key Learnings & Patterns

### Transaction Management
```java
@Transactional  // For write operations
@Transactional(readOnly = true)  // For read operations
```

### Exception Handling
```java
throw new ResourceNotFoundException("Resource not found")
// Caught by GlobalExceptionHandler
```

### DTO Pattern
- Request DTO: Input validation
- Response DTO: Output structure
- Mapper: Entity <-> DTO conversion

### Service Layer Pattern
```java
@Service
@Transactional
public class XyzService {
    // Business logic
    // Coordinate between repositories
    // Exception handling
}
```

## Quick Reference: Next Commands

After DB is running:

1. Build: `mvn clean install`
2. Run: `mvn spring-boot:run`
3. Test: `mvn test`
4. Package: `mvn clean package`

## File Locations Quick Map

- Controllers: `src/main/java/com/marketing/analytics/controller/`
- Services: `src/main/java/com/marketing/analytics/service/`
- Entities: `src/main/java/com/marketing/analytics/entity/`
- DTOs: `src/main/java/com/marketing/analytics/dto/`
- Config: `src/main/java/com/marketing/analytics/config/`
- Security: `src/main/java/com/marketing/analytics/security/`
- Exceptions: `src/main/java/com/marketing/analytics/exception/`
- Tests: `src/test/java/com/marketing/analytics/`

---

**Ready for Phase 2 Implementation**: YES ✓
**Database Schema**: Ready ✓
**Authentication**: Ready ✓
**Next**: Customer Service & Dataset Upload
