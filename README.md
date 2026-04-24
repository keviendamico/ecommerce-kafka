# ecommerce-kafka

A distributed e-commerce system built for learning Apache Kafka with Spring Boot 4 and Java 21.
It implements a **choreography-based saga** pattern with no central orchestrator.

## Architecture

```
Client
  │
  ▼
order-service       (REST API + Kafka Producer)
  │
  │ OrderCreated
  ▼
inventory-service   (Kafka Consumer + Producer)
  │
  ├─ StockReserved ──► payment-service   (Kafka Consumer + Producer)
  │                         │
  │                         ├─ PaymentConfirmed ──► notification-service
  │                         └─ PaymentFailed    ──► notification-service
  │
  └─ StockFailed ──────────────────────────────► notification-service
```

## Modules

| Module | Role | Port |
|---|---|---|
| `shared-events` | Shared Kafka event POJOs (no Spring) | — |
| `order-service` | Exposes REST API, publishes `OrderCreated` | 8080 |
| `inventory-service` | Checks stock, publishes `StockReserved` or `StockFailed` | — |
| `payment-service` | Simulates payment gateway, publishes `PaymentConfirmed` or `PaymentFailed` | — |
| `notification-service` | Sends email notification on every saga outcome | — |

## Kafka Topics

| Topic | Producer | Consumers |
|---|---|---|
| `order.created` | order-service | inventory-service |
| `stock.reserved` | inventory-service | payment-service |
| `stock.failed` | inventory-service | notification-service |
| `payment.confirmed` | payment-service | notification-service |
| `payment.failed` | payment-service | notification-service |

## Tech Stack

- Java 21
- Spring Boot 4.0.6
- Spring Kafka
- Jackson (JSON serialization)
- Spring Mail
- Docker Compose (Kafka + Kafka UI + Mailpit)

## Prerequisites

- Java 21+
- Maven 3.9+
- Docker

## Getting Started

**1. Start infrastructure**
```bash
docker compose up -d
```

This starts:
- Kafka broker on `localhost:9092`
- Kafka UI on `http://localhost:8081`
- Mailpit (SMTP on port 1025, UI on `http://localhost:8025`)

**2. Build**

**3. Start each service** (in separate terminals or with IDE)

## Product Catalog

The inventory is simulated in-memory. The following products are available:

| Product ID | Available Stock |
|---|---|
| `NS00123` | 5 |
| `NS00122` | 1 |
| `NS00127` | 10 |

Any `productId` not in this list will trigger a `StockFailed` event.
Any product with a requested quantity exceeding available stock will also trigger `StockFailed`.

## Test Scenarios

HTTP requests are available in `curls/orders.http` (IntelliJ HTTP Client format).

**Scenario 1 — Happy path**
```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-001",
    "items": [
      { "productId": "NS00123", "quantity": 2, "unitPrice": 29.99 },
      { "productId": "NS00127", "quantity": 3, "unitPrice": 9.99 }
    ]
  }'
```
Expected flow: `OrderCreated` → `StockReserved` → `PaymentConfirmed` or `PaymentFailed` (80/20 random) → email notification.

**Scenario 2 — StockFailed: quantity exceeds stock**
```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-002",
    "items": [
      { "productId": "NS00122", "quantity": 5, "unitPrice": 49.99 }
    ]
  }'
```
`NS00122` has only 1 unit. Expected flow: `OrderCreated` → `StockFailed` → email notification.

**Scenario 3 — StockFailed: unknown product**
```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-003",
    "items": [
      { "productId": "PROD-999", "quantity": 1, "unitPrice": 19.99 }
    ]
  }'
```
`PROD-999` does not exist in the catalog. Expected flow: `OrderCreated` → `StockFailed` → email notification.

## Observability

- **Kafka UI**: `http://localhost:8081` — browse topics, messages, consumer groups and offsets
- **Mailpit**: `http://localhost:8025` — inspect emails sent by notification-service
- **Logs**: each service prefixes its log lines with `[ORDER]`, `[INVENTORY]`, `[PAYMENT]` or `[NOTIFICATION]`