# 🍕 FoodOrderingApp

A modern, full-stack food ordering application built with Angular frontend and microservices architecture backend. This application provides a seamless experience for users to browse restaurants, place orders, and track their deliveries in real-time.

## 📋 Features

- **User Authentication** - Secure sign-up and login functionality with JWT token management
- **Browse Restaurants & Menus** - Interactive menu browsing with filtering capabilities
- **Order Management** - Real-time order status tracking and order history
- **Responsive Design** - Works seamlessly on desktop and mobile devices
- **Message-Driven Architecture** - Kafka-based event streaming for order processing
- **Inter-Service Communication** - gRPC for synchronous service-to-service communication
- **Service Discovery** - Eureka-based dynamic service registration and discovery

## 🏗️ Tech Stack

### Frontend
- **Framework:** Angular 20.2.0
- **Language:** TypeScript 5.9.2
- **UI Components:** Angular Material 20.2.12
- **Styling:** CSS with Material prebuilt themes

### Backend - Microservices Architecture
- **Framework:** Spring Boot 3.5.3
- **Language:** Java 21
- **Service Discovery:** Eureka Server (Netflix)
- **API Gateway:** Spring Cloud Gateway
- **Database:** PostgreSQL
- **Migration:** Flyway for schema management
- **Authentication:** JWT (JSON Web Tokens) with JJWT 0.12.6
- **Dependency Injection:** Spring Cloud Config Server
- **HTTP Clients:** OpenFeign for declarative REST clients

### Inter-Service Communication
- **gRPC** - High-performance RPC framework using Protocol Buffers

**gRPC Services:**
- **Meal Service (Server)**
    - Runs on port 9090
    - Service: `MealService`
    - RPC Method: `CheckMealAvailability(MealCheckRequest) → MealCheckResponse`
    - Checks meal availability and ingredient inventory

- **Order Service (Client)**
    - Runs on port 9091
    - Calls Meal Service via gRPC client
    - Validates orders before persisting

### Asynchronous Messaging
- **Apache Kafka** - Message streaming platform
    - Zookeeper coordination service
    - Topic-based event publishing for order events
    - Spring Kafka integration for producer/consumer patterns

## 🚀 Getting Started

### Prerequisites

- **Node.js** >= 16.x
- **npm** >= 8.x or **yarn**
- **Java** 21+
- **Gradle** (optional, uses wrapper)
- **PostgreSQL** 12+
- Modern web browser

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/michal-debski/FoodOrderingApp.git
   cd FoodOrderingApp
