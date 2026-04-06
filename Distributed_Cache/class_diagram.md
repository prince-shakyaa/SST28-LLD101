# Class Diagram — Distributed Cache (LLD)

```
┌────────────────────────────────────────────────────────────────────────────────────────────┐
│                              DISTRIBUTED CACHE SYSTEM                                      │
└────────────────────────────────────────────────────────────────────────────────────────────┘

  ┌──────────────────────────────────┐
  │         <<interface>>            │
  │       DistributionStrategy       │
  │──────────────────────────────────│
  │ + getNodeIndex(key, n): int      │
  └──────────────────────────────────┘
              △
              │ implements
  ┌──────────────────────────────────┐
  │    ModuloDistributionStrategy    │
  │──────────────────────────────────│
  │ + getNodeIndex(key, n): int      │
  │   → Math.abs(key.hashCode()) % n │
  └──────────────────────────────────┘


  ┌──────────────────────────────────┐
  │         <<interface>>            │
  │         EvictionPolicy<K>        │
  │──────────────────────────────────│
  │ + keyAccessed(key: K): void      │
  │ + evict(): K                     │
  └──────────────────────────────────┘
              △
              │ implements
  ┌──────────────────────────────────┐
  │       LRUEvictionPolicy<K>       │
  │──────────────────────────────────│
  │ - accessOrderList: DLL<K>        │
  │ - nodeMap: Map<K, DLLNode<K>>    │
  │──────────────────────────────────│
  │ + keyAccessed(key: K): void      │
  │ + evict(): K                     │
  └──────────────────────────────────┘
              │ uses
              ▼
  ┌──────────────────────────────────┐         ┌──────────────────────────────────┐
  │       DoublyLinkedList<K>        │────────▶│     DoublyLinkedListNode<K>      │
  │──────────────────────────────────│  uses   │──────────────────────────────────│
  │ - dummyHead: DLLNode<K>          │         │ + key: K                         │
  │ - dummyTail: DLLNode<K>          │         │ + prev: DLLNode<K>               │
  │──────────────────────────────────│         │ + next: DLLNode<K>               │
  │ + addNodeAtTail(key): DLLNode<K> │         └──────────────────────────────────┘
  │ + detachNode(node): void         │
  │ + moveNodeToTail(node): void     │
  │ + getKeyAtHead(): K              │
  │ + removeNodeFromHead(): DLLNode  │
  └──────────────────────────────────┘


  ┌──────────────────────────────────┐
  │         <<interface>>            │
  │            Database              │
  │──────────────────────────────────│
  │ + get(key: String): String       │
  │ + put(key, value: String): void  │
  └──────────────────────────────────┘
              △
              │ implements
  ┌──────────────────────────────────┐
  │         DatabaseImpl             │
  │──────────────────────────────────│
  │ - store: Map<String, String>     │
  │──────────────────────────────────│
  │ + get(key): String               │
  │ + put(key, value): void          │
  └──────────────────────────────────┘


  ┌────────────────────────────────────────────────────────────────┐
  │                        DistributedCache                        │
  │────────────────────────────────────────────────────────────────│
  │ - nodes: List<CacheNode>                                       │
  │ - distributionStrategy: DistributionStrategy                   │
  │ - database: Database                                           │
  │────────────────────────────────────────────────────────────────│
  │ + DistributedCache(numNodes, capacity, strategy, db)           │
  │ + get(key: String): String                                     │
  │ + put(key: String, value: String): void                        │
  │ - getTargetNode(key): CacheNode                                │
  │ + printClusterStatus(): void                                   │
  └────────────────────────────────────────────────────────────────┘
          │ has many                              │ depends on
          ▼                                       ▼
  ┌─────────────────────────────┐      DistributionStrategy  Database
  │          CacheNode          │
  │─────────────────────────────│
  │ - nodeId: int               │
  │ - capacity: int             │
  │ - store: Map<String,String> │
  │ - evictionPolicy: EvictionPolicy<String>       │
  │─────────────────────────────│
  │ + get(key): String          │
  │ + put(key, value): void     │
  │ - evict(): void             │
  │ + containsKey(key): boolean │
  └─────────────────────────────┘
          │ has-a
          ▼
    EvictionPolicy<String>
```

---

## Relationships Summary

| Relationship | From → To |
|---|---|
| Uses (dependency) | `DistributedCache` → `DistributionStrategy` |
| Uses (dependency) | `DistributedCache` → `Database` |
| Composition | `DistributedCache` → `CacheNode` (1 to many) |
| Composition | `CacheNode` → `EvictionPolicy` |
| Composition | `LRUEvictionPolicy` → `DoublyLinkedList` |
| Association | `DoublyLinkedList` → `DoublyLinkedListNode` |
| Realization | `ModuloDistributionStrategy` → `DistributionStrategy` |
| Realization | `LRUEvictionPolicy` → `EvictionPolicy` |
| Realization | `DatabaseImpl` → `Database` |

---

## Key Design Patterns Used

| Pattern | Where |
|---|---|
| **Strategy Pattern** | `DistributionStrategy` and `EvictionPolicy` are both swappable strategies |
| **Dependency Injection** | `DistributedCache` receives strategy + DB; `CacheNode` receives policy |
| **Interface Segregation** | Separate interfaces for eviction, distribution, and database |
