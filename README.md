# Mapua University DOIT Inventory System

A full-stack **Inventory Management System** built with **Angular**, **Spring Boot**, and **MySQL**.  
This application helps manage items, quantities, and records efficiently within a local environment.

---

## Features

- Add, edit, and view inventory items
- Search and filter functionality
- Borrow/Loan items (record borrowing and returning)
- Responsive Angular frontend served by Spring Boot
- MySQL database integration
- Local deployment (no internet required)

---

## Prerequisites

Before running the program, make sure the following are installed on your computer:

1. **Java Development Kit (JDK 17 or newer)**
   - [Download Oracle JDK 17 (official site)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
   - (Optional) You may also use **Eclipse Temurin (Adoptium)**, an open-source equivalent: https://adoptium.net/temurin/releases/
   - Verify installation:
     ```bash
     java -version
     ```

2. **MySQL Server 8.0 (Community Edition)**
   - [Download MySQL 8.0 Community Edition](https://dev.mysql.com/downloads/mysql/)
   - Select version **8.0.x** for best compatibility with Spring Boot.
   - Start the MySQL service after installation.
   - Optionally install **MySQL Workbench 8.0** for easier management.

3. *(Optional for development)* **Node.js and Angular CLI**
   - Only required if you plan to modify and rebuild the Angular frontend.
   - [Download Node.js](https://nodejs.org/)
   - Install Angular CLI:
     ```bash
     npm install -g @angular/cli
     ```

---

## Database Setup

1. Open MySQL and create a new database:
   ```sql
   CREATE DATABASE mapua_inventory;
   ```

2. (Optional) Update the database connection in  
   `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/mapua_inventory
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   server.port=8080
   ```

---

## Building the Project

### Step 1: Build the Angular Frontend
If you’re developing or have made changes to the frontend:
```bash
cd frontend
ng build --configuration production
```

After the build, copy the contents of:
```
dist/<your-angular-app>/
```
into:
```
springboot/src/main/resources/static/
```

> You only need to do this when frontend changes are made.

---

### Step 2: Package the Spring Boot Backend
From the backend project folder (where `pom.xml` is located):
```bash
mvn clean package
```

This will generate a JAR file in:
```
target/mapua-inventory-1.0.jar
```

---

## Running the Application

1. Ensure MySQL is running.
2. Run the JAR file:
   ```bash
   java -jar target/mapua-inventory-1.0.jar
   ```
3. Open your web browser and visit:
   ```
   http://localhost:8080
   ```

The Angular frontend and Spring Boot backend will both run on this single local instance.

---

## Using the Program

Once the program is running:

1. **Open the App:**  
   Go to http://localhost:8080

2. **Create an Equipment Type**
   - Name the equipment type

3. **Add Items:**
   - Navigate to the **Add Item** page.
   - Fill in equipment type, specifications, location, and condition
   - Click **Add Item** to store it in the database.

4. **Borrow / Loan Items:**
   - Use the **Borrow Item** feature to record when an item is borrowed.
   - Click **Borrow** button on item to be loaned.
   - Scan the borrower's ID to mark the item as loaned and to store the record in the database.
   - When the item is returned, click **Return** button on item.
   - Scan the borrower's ID to mark the item as returned.

5. **View Inventory:**
   - The main dashboard shows all stored items.
   - Use the **Search** bar or filters to locate specific entries.

6. **Edit Items:**
   - Click the **Edit** button beside an item to modify details.

7. **Check Records**
   - Navigate to Records to see the history of borrowed items and borrowers.

8. **Backup or Export Data:**
   - Use the **Export** button to download a CSV or backup file.

---

## Troubleshooting

| Problem | Possible Cause | Solution |
|----------|----------------|-----------|
| App doesn’t load | MySQL not running | Start MySQL service before running the JAR |
| “Access denied for user” | Wrong DB credentials | Update username/password in `application.properties` |
| 404 or blank page | Angular build missing | Rebuild Angular and copy `dist/` files into `resources/static/` |

---

## Developer Notes

- **Frontend:** Angular
- **Backend:** Spring Boot
- **Database:** MySQL 8.0
- **Build Tool:** Maven

---

## You’re All Set!

Your Mapua University DOIT Inventory System is now ready to use locally.  
Open http://localhost:8080, start adding items, and manage your inventory with ease.

---

© 2025 Mapua Inventory Team
