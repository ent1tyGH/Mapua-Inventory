# Mapua University DOIT Inventory System with Borrower Feature

## Overview
The Mapua University DOIT Inventory System with Borrower Feature is a full-stack application built with **Spring Boot** and **Angular**.  
This version is packaged for Windows with a one-click launcher (.bat), allowing offline use.

Once installed, simply **double-click the batch file** — it will automatically start:
- MySQL (if not already running)
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

👉 [Download MapuaInventory.zip](https://example.com/MapuaInventorySetup.exe)

The zip contains:

- run_inventory.bat → one-click launcher
- mapua-inventory-1.0.jar → backend + Angular frontend
- MapuaUniversityLogo.ico → icon

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
    2. Launch the backend + frontend JAR
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

REM Step 1: Check if MySQL service is running
sc query MySQL80 | findstr /I "RUNNING" >nul
if %errorlevel% equ 0 (
    echo MySQL is already running.
) else (
    echo Starting MySQL...
    net start MySQL80
    if %errorlevel% neq 0 (
        echo Failed to start MySQL. Please make sure it is installed as a service.
        pause
        exit /b
    )
    REM Wait a few seconds for MySQL to fully start
    timeout /t 5 >nul
)

REM Step 2: Launch Spring Boot backend + Angular frontend
echo Launching Mapua Inventory System...
start "" java -jar "%~dp0mapua-inventory-1.0.jar"

REM Optional: keep console window open for logs
pause

   ```
%~dp0 ensures the batch finds the JAR in the same folder.

Adjust MySQL80 if your MySQL service name is different.

---

## Usage Instructions (PLACEHOLDER)
After the system opens in your browser:
- **Login or Register** to start using the system
- Navigate between:
    -  **Inventory List**
    -  **Borrow Records**
    -  **Borrow Item**
- All data is stored in MySQL locally (`inventory_db` by default)

---

## Uninstallation
To uninstall:
1. Stop the app and MySQL (`net stop MySQL80` if running)
2. Delete the installation folder (e.g., `C:\Program Files\MapuaInventory`)

---

## Notes
- You can reconfigure the MySQL connection by editing:
  ```
  application.properties
  ```
- If you included an embedded JRE, users won’t even need to install JDK.

---

## Developer Build Reference (for maintainers)
If you ever need to rebuild or update the app:
1. Build your Spring Boot JAR:
   ```bash
   mvn clean package
   ```
2. Place the resulting mapua-inventory-1.0.jar in your launcher folder
3. Update the batch file if needed, then test on a clean PC with only JDK 17 and MySQL installed

---

© 2025 Mapua Inventory Team