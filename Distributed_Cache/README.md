# Distributed Cache — Assignment 1 (LLD)

A clean, extensible **Distributed Cache System** designed using Object-Oriented principles in Java.  
Supports pluggable **distribution strategies** and **eviction policies**.

---

## Features

| Feature | Detail |
|---|---|
| Operations | `get(key)`, `put(key, value)` |
| Distribution | Modulo-based (pluggable via interface) |
| Eviction | LRU — Least Recently Used (pluggable via interface) |
| Nodes | Configurable number of cache nodes |
| Capacity | Configurable per-node capacity |
| Cache Miss | Auto-fetches from DB and populates cache |
| DB Write | `put` writes to both cache and database |

---

## Project Structure

```
Distributed_Cache/
├── src/
│   ├── Main.java                            # Entry point / demo
│   ├── cache/
│   │   ├── DistributedCache.java            # Central cache coordinator
│   │   ├── CacheNode.java                   # Individual cache node
│   │   └── storage/
│   │       ├── DoublyLinkedList.java         # O(1) LRU order tracking
│   │       └── DoublyLinkedListNode.java     # Node for the DLL
│   ├── eviction/
│   │   ├── EvictionPolicy.java              # Interface (pluggable)
│   │   └── LRUEvictionPolicy.java           # LRU implementation
│   ├── strategy/
│   │   ├── DistributionStrategy.java        # Interface (pluggable)
│   │   └── ModuloDistributionStrategy.java  # hash(key) % N strategy
│   └── database/
│       ├── Database.java                    # Interface
│       └── DatabaseImpl.java               # In-memory mock
├── class_diagram.md                         # UML class diagram
└── README.md
```

---

## Design Explanation

### 1. How Data is Distributed Across Nodes

The `DistributionStrategy` interface abstracts key routing.  
The current implementation — `ModuloDistributionStrategy` — computes:

```
nodeIndex = Math.abs(key.hashCode()) % numberOfNodes
```

This deterministically assigns every key to exactly one node.  
To swap to **Consistent Hashing**, simply implement `DistributionStrategy` and inject it — no other code changes needed.

### 2. How Cache Miss is Handled

```
get(key)
  └─► DistributionStrategy → CacheNode
        └─► node.get(key) == null?  ← MISS
              └─► Database.get(key)
                    └─► node.put(key, value)  ← populate cache
                          └─► return value
```

On a cache miss, the value is fetched from the `Database`, stored in the resolved `CacheNode`, and returned to the caller.

### 3. How Eviction Works (LRU)

Each `CacheNode` has a fixed capacity and an injected `EvictionPolicy`.  
`LRUEvictionPolicy` uses a **Doubly Linked List + HashMap** combination:

- **HashMap**: maps each key → its DLL node for O(1) lookup  
- **DLL**: tail = most recently used, head = least recently used  

On every `get` or `put`, the key's node is moved to the tail.  
When the node is full, the key at the head (LRU) is evicted in **O(1)** time.

### 4. Extensibility

| Extension Point | How to Extend |
|---|---|
| Add new eviction policy (LFU, MRU) | Implement `EvictionPolicy<K>` interface |
| Add new distribution strategy (Consistent Hashing) | Implement `DistributionStrategy` interface |
| Replace backing store | Implement `Database` interface |
| Change node count / capacity | Pass different values to `DistributedCache` constructor |

---

## How to Compile & Run

```bash
# From Distributed_Cache/
mkdir -p out
javac -sourcepath src -d out src/Main.java
java -cp out Main
```

---

## Sample Output

```
[Cache] Initialized with 3 node(s), capacity=3 each.

======== Scenario 1: Cache MISS (key in DB) ========
[Cache] MISS — key='user:1' not on Node-2. Fetching from DB...
[DB] Fetching key='user:1' from database.
[Node-2] Stored key='user:1', value='Alice'. (1/3 slots used)
get('user:1') = Alice

======== Scenario 2: Cache HIT ========
[Cache] HIT  — key='user:1' found on Node-2
get('user:1') = Alice

======== Scenario 3: PUT new value ========
[Node-2] Stored key='session:abc', value='SessionData-XYZ'. (2/3 slots used)
[DB] Writing key='session:abc' to database.
get('session:abc') = SessionData-XYZ

======== Scenario 4: LRU Eviction ========
[Node-2] Evicted key='user:1' (LRU eviction).
[Node-2] Stored key='temp:k2', value='val2'. (3/3 slots used)

======== Scenario 5: Key NOT in DB ========
[Cache] key='unknown:key' not found in database either.
get('unknown:key') = null
```

---

## Assumptions

- Keys are unique strings.
- Network communication between nodes is not implemented (in-memory LLD exercise).
- The database interface is mocked with an in-memory `HashMap` pre-populated with sample data.
- On `put`, data is written to both the cache node and the database (write-through strategy assumed).
