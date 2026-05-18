# 🍕 FoodOrderingApp

A modern, full-stack food ordering application built with Angular frontend and microservices architecture backend. This application provides a seamless experience for users to browse restaurants, add meals to their shopping cart, manage orders, and track their deliveries in real-time.

## 📋 Features

- **User Authentication** - Secure sign-up and login functionality with JWT token management
- **Browse Restaurants & Menus** - Interactive menu browsing with filtering capabilities
- **Shopping Cart Management** - Redis-backed shopping cart service for persistent cart storage across sessions
- **Order Management** - Real-time order status tracking, order history, and order placement from cart
- **Responsive Design** - Works seamlessly on desktop and mobile devices
- **Message-Driven Architecture** - Kafka-based event streaming for asynchronous order processing
- **Inter-Service Communication** - gRPC for synchronous service-to-service communication
- **Service Discovery** - Eureka-based dynamic service registration and discovery
- **API Gateway** - Centralized routing and load balancing for all microservices

## 🏗️ Tech Stack

### Frontend
- **Framework:** Angular 20.2.0
- **Language:** TypeScript 5.9.2
- **UI Components:** Angular Material 20.2.12
- **Styling:** CSS with Material prebuilt themes
- **State Management:** Shopping Cart service with local state management

### Backend - Microservices Architecture
- **Framework:** Spring Boot 3.5.3
- **Language:** Java 21
- **Service Discovery:** Eureka Server (Netflix)
- **API Gateway:** Spring Cloud Gateway (Port 8222)
- **Primary Database:** PostgreSQL
- **Cache Storage:** Redis (for shopping cart persistence)
- **Migration:** Flyway for schema management
- **Authentication:** JWT (JSON Web Tokens) with JJWT 0.12.6
- **Configuration Server:** Spring Cloud Config Server
- **HTTP Clients:** OpenFeign for declarative REST clients

### Inter-Service Communication
- **gRPC** - High-performance RPC framework using Protocol Buffers
  - **Meal Service (Server)**
    - Runs on port 9090
    - Service: `MealService`
    - RPC Method: `CheckMealAvailability(MealCheckRequest) → MealCheckResponse`
    - Checks meal availability and ingredient inventory
  
  - **Order Service (Client)**
    - Runs on port 9091
    - Calls Meal Service via gRPC client
    - Validates orders before persisting

### Microservices Architecture

| Service | Port | Technology | Purpose |
|---------|------|-----------|---------|
| **API Gateway** | 8222 | Spring Cloud Gateway | Centralized entry point for all requests |
| **Auth Service** | 8220 | Spring Boot + JWT | User authentication and token management |
| **Meal Service** | 8090 | Spring Boot + gRPC | Meal and restaurant menu management |
| **Restaurant Service** | 8092 | Spring Boot | Restaurant information and details |
| **Order Service** | 8091 | Spring Boot + Kafka | Order processing and management |
| **Shopping Cart Service** | 8093 | Spring Boot + Redis | Cart management with persistent storage |
| **User Service** | Variable | Spring Boot | User profile and account management |
| **Config Server** | 8888 | Spring Cloud Config | Centralized configuration management |
| **Discovery Service** | 8761 | Eureka Server | Dynamic service registration |

### Asynchronous Messaging
- **Apache Kafka** - Message streaming platform
  - Zookeeper coordination service
  - Topic-based event publishing for order events
  - Spring Kafka integration for producer/consumer patterns

## 🚀 Getting Started

### Prerequisites

- **Docker** - Latest version
- **Docker Compose** - Latest version
- Modern web browser (Chrome, Firefox, Safari, Edge)
- At least 4GB RAM available for Docker
- Port availability: 80, 8090-8093, 8220-8222, 8761, 8888

### Installation

#### Using Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone https://github.com/michal-debski/FoodOrderingApp.git
   cd FoodOrderingApp
   ```

2. **Start all services**
   ```bash
   docker-compose up -d
   ```

3. **Access the application**
   - Frontend: `http://localhost`
   - API Gateway: `http://localhost:8222`
   - Eureka Dashboard: `http://localhost:8761`

## 📚 Running the Application

### Using Docker Compose

The easiest way to run the entire application stack is with Docker Compose:

```bash
docker-compose up -d
```

The `docker-compose.yml` file defines service dependencies using `depends_on`, which ensures containers start in the correct order. The automatic startup sequence is:

1. **Infrastructure Services**
   - PostgreSQL database (port 5432)
   - Redis cache (port 6379)
   - Zookeeper (port 2181)
   - Kafka (port 29092)

2. **Core Services**
   - Config Server (port 8888)
   - Discovery Service / Eureka (port 8761)

3. **Microservices**
   - Auth Service (port 8220)
   - Meal Service (port 8090)
   - Restaurant Service (port 8092)
   - Shopping Cart Service (port 8093)
   - Order Service (port 8091)
   - User Service
   - API Gateway (port 8222)

4. **Frontend**
   - Angular Frontend (port 80)

### Accessing the Application

Once all services are running, you can access:

- **Frontend Application:** http://localhost
- **API Gateway:** http://localhost:8222
- **Eureka Dashboard (Service Registry):** http://localhost:8761
- **Kibana/Monitoring:** Check individual service health at `/actuator/health` endpoints

### Service Health Checks

After startup, verify all services are healthy:

```bash
# Check each service health
curl http://localhost:8220/actuator/health     # Auth Service
curl http://localhost:8090/actuator/health     # Meal Service
curl http://localhost:8093/actuator/health     # Shopping Cart Service
curl http://localhost:8091/actuator/health     # Order Service
curl http://localhost:8222/actuator/health     # API Gateway
```

### Stopping the Application

To stop all services:

```bash
docker-compose down
```

To stop and remove all data:

```bash
docker-compose down -v
```

### Rebuilding Services

If you've made changes to the code and need to rebuild services:

```bash
# Rebuild all services
docker-compose up -d --build

# Or rebuild specific service
docker-compose up -d --build <service-name>
```

### View Logs

```bash
# View logs from all services
docker-compose logs -f

# View logs from specific service
docker-compose logs -f <service-name>

# Examples:
docker-compose logs -f auth-service
docker-compose logs -f shopping-cart-service
docker-compose logs -f api-gateway-service
```

### Startup Order Guarantee

The `docker-compose.yml` is configured with `depends_on` directives that ensure services start in the correct sequence:

1. **Infrastructure** starts first: `postgres`, `redis`, `zookeeper`
2. **Kafka** waits for: `zookeeper`
3. **Config Server** starts independently
4. **Discovery Service** waits for: `config-service`
5. **Microservices** wait for: `config-service`, `discovery-service`, and their specific dependencies (database, cache, message queue)
6. **API Gateway** waits for: all microservices
7. **Frontend** waits for: `api-gateway-service`

This dependency chain ensures all services are available before dependent services start.

## 🛠️ Architecture Overview

The application follows a microservices architecture pattern with the following layers:

- **API Gateway**: Single entry point for all client requests
- **Microservices**: Independent services handling specific domains
- **Data Layer**: PostgreSQL for persistent storage, Redis for caching
- **Message Queue**: Kafka for asynchronous communication between services
- **Service Registry**: Eureka for dynamic service discovery

## 🔐 Authentication

The application uses JWT (JSON Web Tokens) for stateless authentication:

1. User logs in via Auth Service
2. Auth Service returns JWT token
3. Frontend stores token and includes it in subsequent requests
4. API Gateway validates token and routes requests to appropriate service

## 📝 API Documentation

- **Auth Service**: User registration and authentication endpoints
- **Meal Service**: Retrieve meals, menus, and manage restaurant meals
- **Restaurant Service**: Restaurant information and management
- **Order Service**: Order creation, tracking, and history
- **Shopping Cart Service**: Add/remove items, view cart, manage cart state

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
