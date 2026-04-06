# Pluggable Rate Limiting System

**Assignment 2 — Low Level Design (LLD)**

---

## Problem Statement

Design a rate limiting system for **external resource calls** in a backend system.

- A client calls an API → API calls an internal service → internal service *may* call an external (paid) resource.
- Rate limiting is applied **only at the point of the external resource call**, not at the API gateway.
- Not every client request consumes quota — only those that trigger an external call.

---

## Class Diagram

```
<<interface>>
RateLimiter
+ isAllowed(key: String): boolean
+ getConfig(): RateLimitConfig
        ▲               ▲
        |               |
FixedWindowRateLimiter  SlidingWindowRateLimiter

RateLimitConfig          AlgorithmType (enum)
+ maxRequests: int       FIXED_WINDOW
+ windowDurationMs: long SLIDING_WINDOW
                         TOKEN_BUCKET (future)
                         LEAKY_BUCKET (future)
                         SLIDING_LOG  (future)

RateLimiterFactory
+ create(type, config): RateLimiter

InternalService
- rateLimiter: RateLimiter
- externalClient: ExternalResourceClient
+ handleRequest(customerId, data, needsExternalCall): String

ExternalResourceClient
+ call(requestData: String): String
```

---

## Package Structure

```
Pluggable_Rate_Limiter/
├── src/
│   └── ratelimiter/
│       ├── RateLimiter.java              ← Strategy interface
│       ├── RateLimitConfig.java          ← Immutable config value object
│       ├── AlgorithmType.java            ← Enum for algorithm types
│       ├── FixedWindowRateLimiter.java   ← Fixed Window implementation
│       ├── SlidingWindowRateLimiter.java ← Sliding Window implementation
│       ├── RateLimiterFactory.java       ← Factory (pluggable algorithm selection)
│       ├── ExternalResourceClient.java   ← Simulates paid external API
│       ├── InternalService.java          ← Business logic + rate limit gate
│       └── RateLimiterDemo.java          ← Main demo entry point
└── README.md
```

---

## How to Run

```bash
# Compile
javac -d out src/ratelimiter/*.java

# Run
java -cp out ratelimiter.RateLimiterDemo
```

---

## Design Decisions

### 1. Strategy Pattern (`RateLimiter` Interface)
The `RateLimiter` interface is the core abstraction. Each algorithm (`FixedWindowRateLimiter`, `SlidingWindowRateLimiter`) is a strategy that can be swapped at runtime. Adding a new algorithm (e.g., Token Bucket) requires only a new class implementing the interface — **no changes to business logic**.

### 2. Factory Pattern (`RateLimiterFactory`)
The factory decouples algorithm construction from the caller. Business logic and internal services deal only with the `RateLimiter` interface. Switching from Fixed Window to Sliding Window is one line:
```java
// Before
RateLimiter limiter = RateLimiterFactory.create(AlgorithmType.FIXED_WINDOW, config);
// After
RateLimiter limiter = RateLimiterFactory.create(AlgorithmType.SLIDING_WINDOW, config);
```
`InternalService` code is **completely unchanged**.

### 3. Thread Safety
Both implementations use `ConcurrentHashMap` for per-key lock objects. Synchronization is done per-key (`synchronized(keyLock)`) rather than globally, minimizing contention for different customers while keeping individual key operations atomic.

### 4. Rate Limit Key Flexibility
The key is a `String` — it can represent a customer ID, tenant ID, API key, or external provider name. This is injected at call time, not baked into config.

### 5. SOLID Principles Applied
- **SRP**: Each class has a single responsibility (config, algorithm, factory, service, client are separate).
- **OCP**: New algorithms can be added without modifying existing code.
- **LSP**: Any `RateLimiter` implementation can substitute another.
- **ISP**: The `RateLimiter` interface is minimal and focused.
- **DIP**: `InternalService` depends on the `RateLimiter` abstraction, not on concrete implementations.

---

## Algorithm Details

### Fixed Window Counter

**How it works:**
- Divides time into fixed windows of size `windowDurationMs`.
- Each key maintains a counter that increments on every allowed request.
- When the window expires, the counter resets.

```
Window: [0s ---- 60s]  [60s --- 120s]
         ↑ resets here   ↑ resets here
Counter: 1,2,3,4,5 → BLOCK | 1,2,3,...
```

**Pros:** Simple, constant memory O(keys), fast.  
**Cons:** Burst vulnerability at window boundary. A user can make 5 calls at t=59s and 5 more at t=61s — 10 calls in 2 seconds.

---

### Sliding Window Counter

**How it works:**
- Stores a timestamp for every accepted request in a `Deque<Long>`.
- On each check, evicts timestamps older than `currentTime - windowDurationMs`.
- Counts remaining timestamps to decide if request is allowed.

```
At t=65s, window = [5s, 65s]:
Timestamps: [10, 20, 30, 45, 62]
Only [10,20,30] are evicted → count = 2 remaining → allow
```

**Pros:** Accurate, no boundary burst problem, smooth rate distribution.  
**Cons:** Higher memory O(keys × maxRequests), slightly more computation per request.

---

## Trade-offs Summary

| Aspect            | Fixed Window             | Sliding Window               |
|-------------------|--------------------------|------------------------------|
| Memory            | O(keys)                  | O(keys × maxRequests)        |
| Accuracy          | Approximate              | Exact                        |
| Burst at boundary | Yes (double burst risk)  | No                           |
| Complexity        | Simple                   | Moderate                     |
| Performance       | Faster                   | Slightly slower              |
| Best for          | High-throughput, lenient | Strict fairness / paid APIs  |

**For paid external APIs, Sliding Window is preferred** due to its accuracy — you don't want users to exploit window boundaries to double their quota.

---

## Example Use Case (T1 — 5 calls/minute)

```
Client Request 1 → business logic → no external call needed → served internally
Client Request 2 → business logic → external call needed → rate limiter: OK (1/5) → call made
Client Request 3 → business logic → external call needed → rate limiter: OK (2/5) → call made
...
Client Request 7 → business logic → external call needed → rate limiter: BLOCKED (5/5) → rejected gracefully
```

---

## Future Extensions

To add **Token Bucket**:
1. Create `TokenBucketRateLimiter implements RateLimiter`
2. Add `TOKEN_BUCKET` case in `RateLimiterFactory`
3. Zero other changes required ✅
