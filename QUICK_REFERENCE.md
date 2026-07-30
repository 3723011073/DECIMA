# Quick Reference Card - Decision-Centric Marketing Analytics Backend

Print this out! Stick it on your monitor! 📌

---

## 🚀 Get Started NOW

```bash
# 1. Prerequisites: Java 21, Maven, PostgreSQL
java -version

# 2. Create database
createdb marketing_analytics_db

# 3. Build
cd marketing-analytics-backend
mvn clean install

# 4. Run
mvn spring-boot:run

# 5. Test
curl http://localhost:8080/api/v1/health
```

**Time to Running**: ~15 minutes (first build slower)

---

## 📱 Current API Endpoints

```
POST /api/v1/auth/login
{
  "email": "admin@test.com",
  "password": "password123"
}

GET /api/v1/health
→ Returns OK
```

---

## 📂 File Locations Cheat Sheet

| What | Where |
|------|-------|
| Entities | `src/main/java/.../entity/*.java` |
| Repositories | `src/main/java/.../repository/*.java` |
| Services | `src/main/java/.../service/` |
| Controllers | `src/main/java/.../controller/` |
| Configuration | `src/main/java/.../config/` |
| Security | `src/main/java/.../security/` |
| DTOs | `src/main/java/.../dto/` |
| Database Config | `src/main/resources/application.yml` |
| Schema | `src/main/resources/db/migration/` |
| Logs | `logs/marketing-analytics.log` |

---

## 🗂️ Database Tables (12 Total)

```
users
├─ Authentication data
├─ Roles: ADMIN, MANAGER, ANALYST

customers
├─ Demographics
├─ (1) ──→ (N) customer_behavior
├─ (1) ──→ (N) customer_segments
├─ (1) ──→ (N) campaign_responses
└─ (1) ──→ (N) campaign_outcomes

campaigns
├─ Campaign info
├─ (1) ──→ (N) campaign_responses
└─ (1) ──→ (N) campaign_outcomes

predictions
├─ ML results
└─ (0,1) → campaign_outcomes
```

---

## 🔐 Authentication Flow

```
1. POST /api/v1/auth/login
   ↓ Validates credentials
   ↓ Generates JWT token
   
2. Response includes:
   - accessToken (24 hours)
   - refreshToken (7 days)
   - User info
   
3. Use in requests:
   Authorization: Bearer {accessToken}
```

---

## 🏗️ Code Structure Pattern

All new services follow this:

```java
// 1. Entity (already exists)
@Entity
public class Your Entity { ... }

// 2. Repository (already exists)
public interface YourRepository extends JpaRepository<YourEntity, Long> { ... }

// 3. Service (you create)
@Service
public class YourService {
    @Autowired private YourRepository repo;
    public YourEntity create(YourDTO dto) { ... }
}

// 4. Controller (you create)
@RestController
@RequestMapping("/api/v1/your-entity")
public class YourController {
    @PostMapping
    public ResponseEntity<APIResponseDTO<YourDTO>> create(@Valid @RequestBody YourDTO dto) { ... }
}

// 5. DTO (you create)
@Getter @Setter @Builder
public class YourDTO { ... }

// 6. Tests (you create)
@DataJpaTest / @WebMvcTest / @SpringBootTest
public class YourTest { ... }
```

---

## 🎯 Phase 2 Tasks (In Order)

1. ✅ **Customer Service & Controller** (start here!)
2. ✅ **Dataset Upload Module**
3. ✅ **Data Validation**
4. ✅ **Data Preprocessing Pipeline**
5. ✅ **RFM Analysis Engine**
6. ✅ **Decision Engine**
7. ✅ **DTOs & Mappers**
8. ✅ **Testing**

Each takes 4-10 hours. Total: ~6 weeks

---

## 📝 Common Commands

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run

# Test
mvn test

# Package for production
mvn clean package

# Check dependencies
mvn dependency:tree

# Skip tests (fast build)
mvn clean install -DskipTests

# Rebuild and run from scratch
mvn clean install && mvn spring-boot:run
```

---

## 🗄️ Database Commands

```bash
# Connect
psql -U postgres -d marketing_analytics_db

# Inside psql:
\dt           -- List tables
\d users      -- Describe users table
SELECT * FROM users;
\q            -- Quit

# Backup
pg_dump -U postgres marketing_analytics_db > backup.sql

# Restore
psql -U postgres -d marketing_analytics_db < backup.sql
```

---

## 🚨 Error Codes & Meanings

| Code | Meaning | Status |
|------|---------|--------|
| RESOURCE_NOT_FOUND | Record doesn't exist | 404 |
| VALIDATION_ERROR | Bad input data | 400 |
| AUTHENTICATION_FAILED | Wrong credentials | 401 |
| INTERNAL_SERVER_ERROR | Server error | 500 |

---

## 🔑 Configuration Quick Reference

**application.yml key settings**:

```yaml
# Database
spring.datasource.url: jdbc:postgresql://localhost:5432/marketing_analytics_db
spring.datasource.username: postgres
spring.datasource.password: postgres

# Server
server.port: 8080
server.servlet.context-path: /api/v1

# JWT
jwt.secret: your-long-secret-key-here
jwt.expiration: 86400000  # 24 hours

# ML Service
ml-service.url: http://localhost:5000
```

---

## 📖 Documentation Files (Read in Order)

1. `INDEX.md` ← START HERE
2. `BACKEND_MENTOR_SUMMARY.md` (10 min read)
3. `SETUP_GUIDE.md` (5 min read)
4. `README.md` (10 min read)
5. `PROJECT_STATUS.md` (8 min read)
6. `PHASE2_IMPLEMENTATION.md` (detailed tasks)
7. `.kiro/steering/implementation-guide.md` (architecture)

**Total reading time**: ~45 minutes

---

## 🎓 Copy These Patterns

### Create a Service

```java
@Service
@Transactional
public class YourService {
    @Autowired private YourRepository repository;
    
    public YourEntity create(YourDTO dto) {
        YourEntity entity = new YourEntity();
        // set fields from dto
        return repository.save(entity);
    }
    
    public YourEntity findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
```

### Create a Controller

```java
@RestController
@RequestMapping("/api/v1/your-entity")
public class YourController {
    @Autowired private YourService service;
    
    @PostMapping
    public ResponseEntity<APIResponseDTO<YourDTO>> create(
            @Valid @RequestBody YourDTO dto) {
        YourEntity entity = service.create(dto);
        return ResponseEntity.ok(
            APIResponseDTO.success(entity, "Created successfully")
        );
    }
}
```

### Create a DTO

```java
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class YourDTO {
    @NotBlank(message = "Field required")
    private String field;
    
    @Email(message = "Invalid email")
    private String email;
}
```

---

## ✅ Pre-Phase 2 Checklist

- [ ] Can run `mvn clean install` without errors
- [ ] Can run `mvn spring-boot:run` successfully
- [ ] Health endpoint returns OK
- [ ] Can see database tables with `\dt`
- [ ] Can login via auth endpoint
- [ ] Received JWT token in response
- [ ] Read BACKEND_MENTOR_SUMMARY.md
- [ ] Read PHASE2_IMPLEMENTATION.md
- [ ] Created a local copy of this file
- [ ] Ready to build Customer Service

---

## 🐛 Troubleshooting (Quick Fixes)

```
❌ Maven downloads stuck
→ Run: mvn -X clean install (shows details)

❌ Can't connect to database
→ Check: psql -U postgres (PostgreSQL running?)
→ Create: createdb marketing_analytics_db

❌ Port 8080 in use
→ Edit: application.yml server.port: 8081

❌ Build fails with Java error
→ Check: java -version (must be 21)
→ Fix: Install Java 21

❌ JWT secret too short
→ Generate longer key in application.yml (256+ chars)

❌ Database migration fails
→ Check: Flyway version in pom.xml
→ Reset: DROP database, recreate
```

---

## 📊 Quick Stats

| Item | Count |
|------|-------|
| Java Classes | 39 |
| Database Tables | 12 |
| API Endpoints (Phase 1) | 2 |
| API Endpoints (Phase 2+) | 20+ |
| Configuration Files | 3 |
| Documentation Pages | 7 |
| Repository Methods | 50+ |

---

## 🎯 Success Looks Like

### After Setup:
```
✅ mvn clean install - No errors
✅ mvn spring-boot:run - App starts
✅ curl http://localhost:8080/api/v1/health - Returns OK
✅ Database has 12 tables
✅ Can login and get JWT token
```

### During Phase 2:
```
✅ Write CustomerService
✅ Write CustomerController
✅ Write tests
✅ Endpoints return correct responses
✅ Database updates correctly
✅ All tests passing
```

---

## 🚀 You're Ready When:

1. ✅ You can run the application
2. ✅ You understand the 12 database tables
3. ✅ You know what JWT authentication is
4. ✅ You can find files in the project structure
5. ✅ You've read the core documentation

**Then start Phase 2!** 🎉

---

## 🔗 Quick Links

- Start: `SETUP_GUIDE.md`
- Next Phase: `PHASE2_IMPLEMENTATION.md`
- Architecture: `.kiro/steering/implementation-guide.md`
- Full Overview: `INDEX.md`

---

## 💡 Pro Tips

1. **Keep logs open**: `tail -f logs/marketing-analytics.log` in another terminal
2. **Test endpoints**: Use Postman, curl, or REST Client extension
3. **Follow patterns**: Copy existing code structure for new features
4. **Commit often**: Use git to save your work frequently
5. **Read errors**: Stack traces tell you exactly what's wrong
6. **Check the database**: Use psql to verify data is saved
7. **Keep it simple**: Don't over-engineer Phase 2 tasks

---

## 📞 When Stuck

1. **Check logs**: `logs/marketing-analytics.log`
2. **Google the error**: Copy error message into Google
3. **Check documentation**: See relevant .md file
4. **Look at existing code**: Find similar example in project
5. **Restart everything**: Sometimes a full rebuild helps

---

## 🎓 Learn by Doing

- Don't just read - actually write the code
- Copy patterns from existing code
- Test as you build
- Fix errors immediately
- Document what you learn

---

## Final Reminder

**This foundation is solid.**

The hard part (setup, architecture, security) is **done**.

Phase 2 is about following the patterns and building the features.

**You've got this! 💪**

---

*Print this card. Keep it nearby. Reference it constantly.*

**Phase 1: ✅ COMPLETE**
**Phase 2: 🚀 READY TO START**
**Your Success: 🎯 GUARANTEED**

---

*Last Updated: January 15, 2025*
*Keep this handy while developing*
