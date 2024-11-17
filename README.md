### **Order Management Service**

---

## **Overview**

The **Order Management Service** handles order processing, maintains order history, and facilitates order tracking. It
integrates with the **Payment Service** and **User Management Service** through **Kafka** for asynchronous communication
regarding order status updates and payment verifications.

---

## **Features**

1. **Order Confirmation**: Sends a confirmation to users after a successful purchase, including order details.
2. **Order History**: Allows users to view a list of their past orders.
3. **Order Tracking**: Enables users to track the delivery status of their orders.

---

## **Technologies Used**

- **Spring Boot**: A robust framework for building RESTful APIs and microservices.
- **MySQL**: A relational database used to store and manage order data.
- **Apache Kafka**: A distributed event streaming platform for asynchronous inter-service communication.
- **Spring Data JPA**: Simplifies interactions with MySQL by providing an abstraction layer.
- **Lombok**: Reduces boilerplate code with annotations for generating getters, setters, and more.
- **Swagger/OpenAPI**: Simplifies API documentation and facilitates interactive testing via a UI.

---

## **Setup Instructions**

### **1. Prerequisites**

- **Java 17** or higher
- **MySQL** installed and running on `localhost:3306`
- **Apache Kafka** running with Zookeeper
- **Maven** for dependency management

---

### **2. Clone the Repository**

```bash
git clone https://github.com/udaysisodiya16/order-management-service.git
```

---

### **3. Configure the Application**

Create a new database in MySQL:

```sql
CREATE DATABASE order_management_service;
```

Update the `application.properties` file for MySQL and Kafka configurations:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/order_management_service
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
# Kafka Topic
kafka.topic.payment.notification=payment_notification
kafka.topic.order.notification=order_notification
# Server Configuration
server.port=8084
```

---

### **4. Build and Run**

- Build the application:
  ```bash
  mvn clean install
  ```
- Run the application:
  ```bash
  mvn spring-boot:run
  ```

The application will be accessible at `http://localhost:8084`.

---

### **5. Access Swagger API Documentation**

- After the application starts, access Swagger UI for API testing and documentation at:  
  [http://localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html)

---

## **API Endpoints**

### **1. Create Order**

- **Endpoint**: `POST /order`
- **Description**: Places a new order.
- **Request Body** (JSON):
   ```json
   {
       "userId": 1,
       "items": [
           {
               "productId": "123",
               "price": 100.0,
               "quantity": 2
           }
       ],
       "deliveryAddress": "123 Main Street"
   }
   ```
- **Response**:
   ```json
  {
  "orderId": 10,
  "createdAt": "2024-11-17T14:47:16.982Z",
  "totalAmount": 200.0,
  "status": "PENDING",
  "items": [
    {
      "productId": "123",
      "price": 100.0,
      "quantity": 2
    }
    ]
  }
   ```

---

### **2. Get Order Details**

- **Endpoint**: `GET /order/{orderId}`
- **Description**: Retrieves the given order.
- **Request Parameters**:
    - `orderId`.
- **Response**:

```json
  {
  "orderId": 10,
  "createdAt": "2024-11-17T14:47:16.982Z",
  "totalAmount": 200.0,
  "status": "PENDING",
  "items": [
    {
      "productId": "123",
      "price": 100.0,
      "quantity": 2
    }
  ]
}
```

---

### **3. Get Order History**

- **Endpoint**: `GET /order/{userId}/history`
- **Description**: Retrieves the user's past orders.
- **Request Parameters**:
    - `userId`: The ID of the user.
- **Response**:

```json
[
  {
    "orderId": 10,
    "createdAt": "2024-11-17T14:47:16.982Z",
    "totalAmount": 200.0,
    "status": "PENDING",
    "items": [
      {
        "productId": "123",
        "price": 100.0,
        "quantity": 2
      }
    ]
  },
  {
    "orderId": 15,
    "createdAt": "2024-11-17T14:47:16.982Z",
    "totalAmount": 200.0,
    "status": "CANCELLED",
    "items": [
      {
        "productId": "138",
        "price": 370.0,
        "quantity": 1
      }
    ]
  }
]
```

### **4. Track Order**

- **Endpoint**: `GET /order/{orderId}/track`
- **Description**: Tracks the delivery status of an order.
- **Request Parameters**:
    - `orderId`: The ID of the order.
- **Response**:

```json
[
  {
    "orderId": 100,
    "createdAt": "2024-11-17T14:57:14.714Z",
    "totalAmount": 200.0,
    "status": "PENDING",
    "timestamp": "2024-11-17T14:57:14.714Z"
  }
]
```

### **5. Update Order Status**

- **Endpoint**: `PUT /order/{orderId}/status`
- **Description**: Updates the status of an order (used by admin or other services).
- **Request Parameters**:
    - `orderId`: The ID of the order.
    - `orderStatus`: new order status (Available values : PENDING, CONFIRMED, SHIPPED, OUT_FOR_DELIVERY, DELIVERED,
      CANCELLED, RETURNED)

- **Response**:

```json
true
```

### **6. Get Order Status**

- **Endpoint**: `PATCH /order/{orderId}/status`
- **Description**: Get the status of an order.
- **Request Parameters**:
    - `orderId`: The ID of the order.
- **Response**:
  (Available values : PENDING, CONFIRMED, SHIPPED, OUT_FOR_DELIVERY, DELIVERED,
  CANCELLED, RETURNED)

```json
"PENDING"
```

---
