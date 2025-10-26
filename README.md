# Mapua University DOIT Inventory System with Borrower Feature

## Overview
The Mapua University DOIT Inventory System with Borrower Feature is a full-stack application built with **Spring Boot** and **Angular**.  
This version is packaged for Windows with a one-click launcher (.bat), allowing offline use.

Once installed, simply **double-click the batch file** — it will automatically start:
- MySQL (if not already running)
  - You will be asked for your root password.
- The backend server (Spring Boot)
- The web frontend (Angular build inside Spring Boot)

You can then open the app in your browser and use it instantly.
- Open your web browser and go to 'http://localhost:8080' to use the app.

---

## Prerequisites

Before installing, make sure these are installed on the target computer:

### 1️⃣ [Java SE Development Kit 17 (JDK 17)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Download the **Windows x64 Installer** from Oracle (e.g., `jdk-17.0.12_windows-x64_bin.exe`).
- During installation, check “Add JAVA_HOME to PATH.”

To verify installation:
```bash
java -version
```

### 2️⃣ [MySQL 8.0 Community Edition](https://dev.mysql.com/downloads/installer/)
- Download **MySQL Installer for Windows**.
- Install the following components:
    - MySQL Server 8.0
    - MySQL Workbench (optional for viewing data)
- Remember your MySQL root password (you’ll need it once).

---

## Installation Steps

### Step 1 — Download the App
Download the launcher package:

👉 [Download MapuaInventory.zip](https://github.com/ent1tyGH/Mapua-Inventory/releases/tag/v1.0)

The zip contains:

- run_inventory.bat → one-click launcher
- mapua-inventory-1.0.jar → backend + Angular frontend

---

### Step 2 — Extract the Folder
1. Extract the zip to any folder (e.g., C:\Program Files\MapuaInventory)
2. Ensure both .bat and .jar are in the same folder

---

### ▶️ Step 3 — Run the App
After installation:
1. Double-click run_inventory.bat
2. The batch file will:
   1. Check if MySQL is running; start it if needed
   2. Ask for your root password
   3. Launch the backend + frontend JAR
3. Open your browser at:
   👉 http://localhost:8080

---

## How the One-Click Batch Works

Behind the scenes:
   ```bat
   @echo off
REM --------------------------------------
REM One-click launcher for Mapua Inventory
REM --------------------------------------

REM Step 1: Detect and start MySQL service
setlocal enabledelayedexpansion
set SERVICE_FOUND=0

for %%S in (MySQL80 MySQL mysql mysql80) do (
    sc query "%%S" | findstr /I "RUNNING" >nul
    if !errorlevel! equ 0 (
        echo MySQL service "%%S" is already running.
        set SERVICE_FOUND=1
        set SERVICE_NAME=%%S
        goto :skipstart
    )
)

REM No running service found; try to start first available service
for %%S in (MySQL80 MySQL mysql mysql80) do (
    sc query "%%S" >nul 2>&1
    if !errorlevel! equ 0 (
        echo Attempting to start MySQL service "%%S"...
        net start "%%S"
        if !errorlevel! equ 0 (
            echo MySQL started successfully.
            set SERVICE_NAME=%%S
            set SERVICE_FOUND=1
            goto :skipstart
        ) else (
            echo Could not start "%%S". Trying next service name...
        )
    )
)

:skipstart
if !SERVICE_FOUND! equ 0 (
    echo Could not find a MySQL service. Please ensure MySQL is installed as a Windows service.
    pause
)

REM Step 2: Prompt for MySQL root password (optional)
set /p MYSQL_PASS=Enter MySQL root password (leave blank to skip):
if not "%MYSQL_PASS%"=="" set SPRING_DATASOURCE_PASSWORD=%MYSQL_PASS%

REM Step 3: Launch backend + frontend JAR
echo Launching Mapua Inventory System...
java -jar "%~dp0mapua-inventory-1.0.jar"

pause
endlocal

   ```
**Developer Notes:**

- %~dp0 ensures the batch uses the folder it resides in, so you can place .bat and .jar together anywhere.
- SPRING_DATASOURCE_PASSWORD will be picked up by Spring Boot if your application.properties is configured to use ${SPRING_DATASOURCE_PASSWORD}.
- The pause at the end keeps the console open so you can see logs/errors.
---

## Usage Instructions

## 🧭 Table of Contents
- [👥 For Users](#-for-users)
- [🛠️ For Admins](#-for-admins)
- [💾 Database Backup](#-database-backup)
- [🖥️ System Overview](#-system-overview)
- [🎨 Design Notes](#-design-notes)

---

## 👥 For Users

### 🔍 Borrowing an Item
1. Go to the **Item Page**.
2. **Search** for the item you want to borrow.
3. **Tap your ID** to check if you’re already registered in the database.
    - If **you are not in the database**, fill out the following:
        - 🧑 Name
        - 🎓 Student ID
4. Once verified, the **item will be marked as borrowed**.

---

### 🔄 Returning an Item
1. Go to the **Return Page**.
2. **Tap your ID** to view all your borrowed items.
3. Select the item(s) you wish to return.
4. Fill out the **Return Form**:
    - 📝 Remarks
    - ⚙️ Condition (e.g., Good, Slightly Damaged, Broken)
5. Confirm to complete the return.

> ✅ *Returned items will automatically update in the database.*

---

## 🛠️ For Admins

### 🧾 Managing Equipment

#### ➕ Add Equipment Type
1. Go to the **Equipment Page**.
2. Add a new **Equipment Type** (e.g., Camera, Tripod, Laptop, etc.).

#### 🧩 Add Items
1. Go to the **Item Page**.
2. Provide the following details:
    - Select the Equipment type
    - Specification
    - Location
    - Condition (Working, Needs Repair, or Disposed)

> 🗂️ *Each item is linked to an equipment type for easy organization.*

---

### 📅 Daily Reports
- Navigate to the **Daily Report Page**.
- Record daily activities such as borrowed items, returns, or maintenance logs.

> 🧮 *Reports help track equipment usage trends and monitor student activity.*

---

## 💾 Database Backup
- Access the **Backup Option** from the **Navigation Bar**.
- Click **Backup Database** to save a secure copy of all system data.

---

## 🖥️ System Overview
- **Frontend:** Angular (modern responsive design)
- **Backend:** Node.js / Express (REST API)
- **Database:** MySQL
- **Theme:** 🟥 Mapúa-inspired color palette with red and gold accents

---

## 🎨 Design Notes
- Clean, responsive interface for desktop and mobile users.
- Color-coded indicators:
    - 🟢 Available items
    - 🔴 Borrowed items
    - 🟡 need repair
- Red text for **unreturned items** for quick visibility.
- Tables and cards styled for readability and alignment consistency.

---

## Uninstallation
To uninstall:
1. Stop the app and MySQL (via Task Manager)
2. Delete the installation folder (e.g., `C:\Program Files\MapuaInventory`)

---

## Notes
- You can reconfigure the MySQL connection by editing:
  ```
  application.properties
  ```

---

## Developer Build Reference (for maintainers)
If you ever need to rebuild or update the app:
1. Build you Angular project
    ```bash
   ng build --configuration production
   ```
2. Navigate to **dist/project-name** folder and copy the contents into spring-boot **src/main/resources/static**
3. Build your Spring Boot JAR:
   ```bash
   mvn clean package
   ```
4. Place the resulting mapua-inventory-1.0.jar in your launcher folder
5. Update the batch file if needed, then test on a clean PC with only JDK 17 and MySQL installed

---

© 2025 Mapua Inventory Team