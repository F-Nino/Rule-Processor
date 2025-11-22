# Architecture & Design Decisions


## Validation Strategy


### Chosen Approach: Rule Interface Pattern
Create a `ValidationRule` interface with concrete implementations for each rule
- **Rationale**:
- Quick to implement under time constraints
- Easy to add new rules in the future
- Enables isolated unit testing for each rule
- Clear separation of concerns
- Assume no authentication is needed for current task
- No database for now (if application is valid we could store in the future)

### Rule Execution Strategy
- **No Priority System**: All rules execute and return violations at once
- **No Severity Levels**: All violations are treated equally
- **Future Enhancement**: Could implement rule priorities or severity levels if needed


## Conflict Detection


### Current State
- No rule conflicts identified in the current rule set
- All rules can be evaluated independently


### Future Considerations
- **Conflict Detection Strategy**:
- Identify rules with overlapping field requirements
- Flag rules that could produce contradictory violations


## Performance & Scalability


### Current Performance Profile
- **Rule Count**: 7 rules
- **Execution Time**: Constant per rule (no external calls)
- **Bottleneck**: Not a concern at current scale


### Optimization for Scale Thoughts
Potential bottlenecks if rules include:
- External API calls
- Database lookups
- Complex calculations


**Solutions**:
- Batch application processing
- Cache frequently accessed data


### High-Volume Requests


**Architecture Strategy**:


1. **Horizontal Scaling**
- Deploy multiple instances behind a load balancer
- Auto-scale based on queue depth or CPU metrics


2. **Async Batch Processing**
- Queue-based system (AWS SQS, Apache Kafka)
- Support bulk uploads for batch validation


3. **Parallel Processing**
- Each instance processes multiple applications concurrently
- Thread pool sizing based on I/O vs CPU-bound operations


## Data Model


### Core Classes


#### `Application`
Main entity containing all student application data


#### `StudentInfo`
- `firstName`: String
- `lastName`: String
- `ssn`: String
- `dateOfBirth`: LocalDate


#### `SpouseInfo`
- `firstName`: String
- `lastName`: String
- `ssn`: String


#### `Household`
- `numberInHousehold`: Integer
- `numberInCollege`: Integer


#### `Income`
- `studentIncome`: Integer
- `parentIncome`: Integer


#### Enums
- `MaritalStatus`: SINGLE, MARRIED
- `DependencyStatus`: DEPENDENT, INDEPENDENT


#### Other Fields
- `stateOfResidence`: String (2-letter state code)


### Response Models


#### `RuleViolation`
- `fieldName`: String
- `message`: String


#### `ValidationResponse`
- `valid`: Boolean
- `violations`: List&lt;RuleViolation&gt;

