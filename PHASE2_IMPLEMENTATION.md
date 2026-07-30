# Phase 2: Implementation Tasks - Decision-Centric Marketing Analytics

**Duration**: 4-6 weeks
**Status**: Ready to Start
**Prerequisite**: Phase 1 Complete ✅

---

## Overview

Phase 2 focuses on implementing core business services that handle customer data, dataset processing, RFM analysis, and customer segmentation.

### Phase 2 Deliverables
1. Customer Management Service & APIs
2. Dataset Upload & Processing Module
3. CSV Parser & Data Validator
4. Data Preprocessing Pipeline
5. RFM Analysis Engine
6. Customer Segmentation
7. Decision Engine Foundation

---

## Task 1: Customer Service & Controller

**Duration**: 4-5 hours
**Files to Create**: 2
**Complexity**: Low-Medium

### 1.1 Create CustomerService

**File**: `src/main/java/com/marketing/analytics/service/CustomerService.java`

**Responsibilities**:
- Customer CRUD operations
- Filtering and searching
- Bulk operations
- Data validation

**Key Methods**:
```java
// Read operations
Page<Customer> getAllCustomers(Pageable pageable)
Customer getCustomerById(Long customerId)
List<Customer> searchCustomers(CustomerSearchFilter filter)

// Write operations
Customer createCustomer(CustomerDTO dto)
Customer updateCustomer(Long customerId, CustomerDTO dto)
void deleteCustomer(Long customerId)

// Bulk operations
List<Customer> createCustomersFromDataset(List<Map<String, Object>> records)
```

**Implementation Notes**:
- Use Spring Data's Page<T> for pagination
- Implement search with dynamic filtering
- Add validation before create/update
- Handle duplicate detection

### 1.2 Create CustomerController

**File**: `src/main/java/com/marketing/analytics/controller/CustomerController.java`

**Endpoints**:
```
GET    /api/v1/customers                     - List all (paginated)
GET    /api/v1/customers/{id}                - Get by ID
POST   /api/v1/customers                     - Create new
PUT    /api/v1/customers/{id}                - Update
DELETE /api/v1/customers/{id}                - Delete
GET    /api/v1/customers/search              - Search with filters
GET    /api/v1/customers/{id}/behavior       - Get customer behavior
```

**Request/Response Examples**:

```json
// GET /api/v1/customers?page=0&size=20
{
  "success": true,
  "message": "Customers retrieved",
  "data": {
    "content": [
      {
        "customerId": 1,
        "age": 35,
        "gender": "M",
        "income": 75000.0,
        "education": "Bachelor",
        "maritalStatus": "Married"
      }
    ],
    "totalElements": 150,
    "totalPages": 8,
    "currentPage": 0
  }
}

// POST /api/v1/customers
{
  "age": 35,
  "gender": "M",
  "income": 75000.0,
  "education": "Bachelor",
  "maritalStatus": "Married"
}
```

---

## Task 2: Dataset Upload Module

**Duration**: 6-8 hours
**Files to Create**: 3
**Complexity**: Medium

### 2.1 Create DatasetUploadService

**File**: `src/main/java/com/marketing/analytics/service/DatasetUploadService.java`

**Responsibilities**:
- Handle file uploads
- Validate CSV format
- Store metadata
- Track processing status
- Error logging

**Key Methods**:
```java
UploadedDataset uploadDataset(
    MultipartFile file, 
    Long userId
) throws Exception

void validateCSVStructure(File file) throws IOException

List<Map<String, Object>> parseCSVContent(File file) throws IOException

void updateDatasetStatus(Long datasetId, DatasetStatus status)

void updateDatasetError(Long datasetId, String errorMessage)
```

**Implementation Notes**:
- Use Apache Commons CSV for parsing
- Validate headers match expected columns
- Store file on disk with unique naming
- Update status in database
- Log all errors

### 2.2 Create DatasetUploadController

**File**: `src/main/java/com/marketing/analytics/controller/DatasetUploadController.java`

**Endpoints**:
```
POST   /api/v1/datasets/upload           - Upload CSV
GET    /api/v1/datasets/{id}             - Get upload status
GET    /api/v1/datasets                  - List uploads
DELETE /api/v1/datasets/{id}             - Delete upload
GET    /api/v1/datasets/{id}/preview     - Preview data (first 10 rows)
```

**Implementation**:
```java
@PostMapping("/upload")
public ResponseEntity<APIResponseDTO<UploadedDataset>> uploadDataset(
    @RequestParam("file") MultipartFile file,
    @CurrentSecurityContext(expression="authentication.principal.userId") Long userId
)
```

### 2.3 Create CSV Parser Utility

**File**: `src/main/java/com/marketing/analytics/util/CSVParserUtil.java`

**Methods**:
```java
List<String> extractHeaders(File csvFile) throws IOException

List<Map<String, Object>> parseCSV(File csvFile) throws IOException

void validateCSVHeaders(List<String> headers) throws IOException

Map<String, Object> parseRow(CSVRecord record)
```

**Expected CSV Structure**:
```csv
customer_id,age,gender,income,education,marital_status,website_visits,purchase_count,last_purchase_date,total_spending,engagement_score
1,35,M,75000,Bachelor,Married,45,12,2024-12-01,8500.50,0.85
2,28,F,65000,Master,Single,32,8,2024-11-15,5200.00,0.72
```

---

## Task 3: Data Validation Module

**Duration**: 3-4 hours
**Files to Create**: 2
**Complexity**: Low-Medium

### 3.1 Create DataValidator

**File**: `src/main/java/com/marketing/analytics/util/DataValidator.java`

**Validation Rules**:
```java
// Age: 18-120
// Income: > 0
// Education: One of [High School, Bachelor, Master, PhD]
// Gender: M, F, Other
// Marital Status: Single, Married, Divorced, Widowed
// Website Visits: >= 0
// Purchase Count: >= 0
// Engagement Score: 0-1
```

**Methods**:
```java
ValidationResult validateCustomerRecord(Map<String, Object> record)

List<ValidationError> getValidationErrors(Map<String, Object> record)

boolean isValidEmail(String email)
boolean isValidAge(Integer age)
boolean isValidIncome(Double income)
```

### 3.2 Create ValidationResult DTO

**File**: `src/main/java/com/marketing/analytics/dto/ValidationResult.java`

```java
@Getter
@Setter
@Builder
public class ValidationResult {
    private boolean valid;
    private List<String> errors;
    private int rowNumber;
    private Map<String, Object> record;
}
```

---

## Task 4: Data Preprocessing Pipeline

**Duration**: 8-10 hours
**Files to Create**: 5
**Complexity**: Medium-High

### 4.1 Create Preprocessing Module

**Files**:
```
src/main/java/com/marketing/analytics/preprocessing/
├── DataPreprocessor.java       (Orchestrator)
├── MissingValueHandler.java
├── DataNormalizer.java
├── OutlierDetector.java
└── FeatureExtractor.java
```

### 4.2 DataPreprocessor (Main Orchestrator)

**Methods**:
```java
void preprocessDataset(UploadedDataset dataset)

void handleMissingValues(List<Map<String, Object>> records)

void normalizeData(List<Map<String, Object>> records)

void detectOutliers(List<Map<String, Object>> records)

List<Map<String, Object>> extractFeatures(List<Map<String, Object>> records)
```

### 4.3 MissingValueHandler

**Strategies**:
```
- Age: Fill with median
- Income: Fill with mean
- Engagement Score: Fill with 0
- Last Purchase: Set to null if missing (no substitute)
```

### 4.4 DataNormalizer

**Operations**:
```
- Age: Normalize to 0-1 using min-max
- Income: Normalize to 0-1 using min-max
- Website Visits: Normalize to 0-1
- Purchase Count: Normalize to 0-1
- Total Spending: Normalize to 0-1
```

### 4.5 Feature Extractor

**New Features to Create**:
```java
// Customer Lifecycle Features
customer_lifetime_value = total_spending
purchase_frequency = purchase_count / (days_since_first_purchase)
average_order_value = total_spending / purchase_count

// Engagement Features
visit_conversion_rate = purchase_count / website_visits
days_since_purchase = current_date - last_purchase_date

// Demographic Features
age_group = categorize_age(age)
income_group = categorize_income(income)
```

---

## Task 5: RFM Analysis Engine

**Duration**: 6-8 hours
**Files to Create**: 2
**Complexity**: Medium

### 5.1 Create RFMAnalysisService

**File**: `src/main/java/com/marketing/analytics/service/RFMAnalysisService.java`

**Methods**:
```java
void performRFMAnalysis(UploadedDataset dataset)

void calculateRecency(List<Customer> customers)

void calculateFrequency(List<Customer> customers)

void calculateMonetary(List<Customer> customers)

void assignRFMScores(List<Customer> customers)

void segmentCustomers(List<Customer> customers)
```

### 5.2 RFM Calculation Logic

**Recency (R)**:
```
Days since last purchase:
0-7 days:    Score 5
8-30 days:   Score 4
31-90 days:  Score 3
91-180 days: Score 2
180+ days:   Score 1
```

**Frequency (F)**:
```
Number of purchases:
20+:  Score 5
10-19: Score 4
5-9:   Score 3
2-4:   Score 2
1:     Score 1
```

**Monetary (M)**:
```
Total spending quartiles:
Top 25%:    Score 5
50-75%:     Score 4
25-50%:     Score 3
10-25%:     Score 2
Bottom 10%: Score 1
```

### 5.3 Segmentation Logic

**Segments**:
```
Champions:        R=5, F=5, M=5 (or similar high scores)
Loyal:           R=4-5, F=4-5, M=4-5
At Risk:         R=1-2, F=3-4, M=3-5
Lost:            R=1, F=1-2, M=1-2
Dormant:         R=1, F=1, M=1
Potential:       R=4-5, F=1-2, M=1-3
```

---

## Task 6: Decision Engine Foundation

**Duration**: 4-6 hours
**Files to Create**: 2
**Complexity**: Medium

### 6.1 Create DecisionEngineService

**File**: `src/main/java/com/marketing/analytics/service/DecisionEngineService.java`

**Responsibilities**:
- Analyze customer segment
- Review prediction (from ML)
- Consider historical performance
- Generate recommendation

**Methods**:
```java
MarketingDecision generateRecommendation(Customer customer, Prediction prediction)

String selectBestChannel(CustomerSegment segment)

String selectBestCampaignType(CustomerSegment segment, RFMScore rfmScore)

Double calculateExpectedROI(CustomerSegment segment, String campaignType)

String generateActionMessage(MarketingDecision decision)
```

### 6.2 Create MarketingDecision DTO

```java
@Getter
@Setter
@Builder
public class MarketingDecision {
    private Long customerId;
    private String recommendedSegment;
    private String recommendedChannel;      // Email, SMS, Push, Direct
    private String recommendedCampaignType; // Promotional, Educational, etc
    private Double expectedROI;
    private Double confidence;
    private String actionMessage;
    private LocalDateTime generatedAt;
}
```

---

## Task 7: Additional DTOs & Mappers

**Duration**: 3-4 hours
**Files to Create**: 8
**Complexity**: Low

### DTOs to Create
```
src/main/java/com/marketing/analytics/dto/
├── CustomerCreateDTO.java
├── CustomerUpdateDTO.java
├── CustomerSearchFilterDTO.java
├── UploadedDatasetDTO.java
├── CustomerSegmentDTO.java
├── RFMScoreDTO.java
├── MarketingDecisionDTO.java
└── DatasetPreviewDTO.java
```

### Mappers to Create
```
src/main/java/com/marketing/analytics/mapper/
├── CustomerMapper.java
├── DatasetMapper.java
└── SegmentMapper.java
```

---

## Task 8: Testing

**Duration**: 8-10 hours (can be done in parallel)
**Files to Create**: 8
**Complexity**: Low-Medium

### Unit Tests
```
src/test/java/com/marketing/analytics/
├── service/
│   ├── CustomerServiceTest.java
│   ├── DatasetUploadServiceTest.java
│   ├── RFMAnalysisServiceTest.java
│   └── DecisionEngineServiceTest.java
├── util/
│   ├── CSVParserUtilTest.java
│   ├── DataValidatorTest.java
│   └── CSVParserUtilTest.java
└── controller/
    └── CustomerControllerTest.java
```

### Integration Tests
```
src/test/java/com/marketing/analytics/integration/
├── CustomerServiceIntegrationTest.java
└── DatasetUploadIntegrationTest.java
```

---

## Implementation Order & Dependencies

```
Week 1:
  ├─ Task 1: Customer Service (Parallel with Task 2)
  └─ Task 2: Dataset Upload Module

Week 2:
  ├─ Task 3: Data Validation
  └─ Task 4: Data Preprocessing

Week 2-3:
  ├─ Task 5: RFM Analysis Engine
  └─ Task 6: Decision Engine

Week 3:
  ├─ Task 7: DTOs & Mappers
  └─ Task 8: Testing (Ongoing)

Week 4 (Buffer):
  └─ Bug fixes, optimization, documentation
```

---

## Acceptance Criteria for Phase 2

### Customer Service
- [x] All CRUD endpoints working
- [x] Pagination functional
- [x] Search filters working
- [x] Error handling complete
- [x] Unit tests passing

### Dataset Upload
- [x] File upload working
- [x] CSV validation complete
- [x] Status tracking accurate
- [x] Error messages clear
- [x] File stored securely

### Data Preprocessing
- [x] All preprocessing steps working
- [x] Missing values handled
- [x] Data normalized correctly
- [x] Outliers detected
- [x] Features extracted

### RFM Analysis
- [x] Recency calculated correctly
- [x] Frequency calculated correctly
- [x] Monetary calculated correctly
- [x] RFM scores assigned properly
- [x] Segmentation accurate

### Decision Engine
- [x] Recommendations generated
- [x] Channel selection working
- [x] ROI calculation accurate
- [x] Confidence scores reasonable

---

## Quality Metrics

Target for Phase 2:
- Code coverage: > 70%
- API response time: < 200ms
- Database query time: < 100ms
- Error handling: 100% of exceptions caught

---

## Documentation to Update

- [ ] README.md - Add new endpoints
- [ ] API documentation - Swagger/OpenAPI
- [ ] Database schema - Add any new tables
- [ ] Architecture diagram - Update flow
- [ ] Deployment guide - Update config

---

## Known Risks & Mitigations

| Risk | Impact | Mitigation |
|------|--------|-----------|
| CSV parsing errors | High | Comprehensive validation, error logging |
| Large dataset processing | High | Batch processing, pagination |
| Data inconsistencies | Medium | Data validation, normalization |
| ML service integration delay | High | Mock ML service for testing |

---

## Success Checklist

After Phase 2 completion:
- [ ] All endpoints tested manually
- [ ] Automated tests passing
- [ ] Documentation complete
- [ ] Code reviewed
- [ ] Performance acceptable
- [ ] Security validated
- [ ] Ready for Phase 3 integration

---

**Phase 2 Start Date**: Ready Now
**Estimated Completion**: 4-6 weeks
**Next Phase**: Phase 3 - ML Integration

---

*Document Version: 1.0*
*Last Updated: 2025-01-15*
