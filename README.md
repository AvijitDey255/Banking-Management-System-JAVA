# 💳 Banking Management System (Java)

A simple **console-based banking system** in Java that allows users to **sign up, log in, deposit, withdraw, and log out**.  
All account details are stored in `details.txt`, including **account number, username, password, balance, and last login date**.

---

## ✨ Features

- **User Authentication**
  - Sign Up (create a new account)
  - Login & Logout
- **Banking Operations**
  - View account details
  - Check balance
  - Deposit money
  - Withdraw money (with balance validation)
- **Data Storage**
  - Saves accounts in `details.txt`
  - Tracks last login date in `YYYY-MM-DD` format

---

## 🖥 Example `details.txt` Format

- AccountNumber=1001, Username=ram, Password=1234, Balance=5000.0, LastLogin=15-08-2025
- AccountNumber=1002, Username=shyam, Password=abcd, Balance=2000.0, LastLogin=15-08-2025

**Format:**  
AccountNumber,Username,Password,Balance,LastLoginDate
---

## 🚀 How to Run

```bash
https://github.com/AvijitDey255/Banking-Management-System-JAVA.git
cd Banking-Management-System
javac BankingSystem.java
java BankingSystem


