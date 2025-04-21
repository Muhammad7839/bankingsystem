# 💰 EverGreen Finance – Java Banking System

**EverGreen Finance** is a complete Java-based banking system created as a term project for BCS 345-J01 at Farmingdale State College. It simulates core banking functionalities such as account management, secure customer login, transaction processing, and receipt generation using JavaFX and file handling.

---

## 📌 Features

- **User Authentication**
  - Secure login using Customer ID and PIN (3 attempts max).
  - Each customer is uniquely identified.

- **Account Management**
  - Supports checking and savings accounts.
  - Displays account number, type, and current balance.

- **Transactions**
  - Deposit and withdraw funds.
  - Transfer funds between accounts (debit one, credit another).
  - Automatically updates account balances.

- **Receipt Generation**
  - Displays custom-designed receipts for every transaction.
  - Receipt includes:
    - Bank Name and Address
    - Customer Name and Address
    - Account Number
    - Transaction Type and Amount
    - Date and Time stamp

- **File Handling**
  - Loads customer and account data from text files.
  - Logs all transactions to an output file at the end.
  - Uses exception handling for input/output safety.

- **UI with JavaFX**
  - Clean and user-friendly interface.
  - Includes visual confirmations, error messages, and personalized messages.

---

## 🗂️ Project Structure

- `Customer.java` – Customer details and validation.
- `Account.java` – Account setup, balance operations.
- `Transaction.java` – Handles deposits, withdrawals, and transfers.
- `Main.java` / `EverGreenFinance.java` – Entry point with login and main logic.
- `*.txt` – Input files for customers and accounts, output for transaction logs.

---

## 📎 Technologies Used

- Java (OOP, File I/O, Exception Handling)
- JavaFX (GUI)
- Text Files for Data Storage

---

## 🚀 Getting Started
Open the project in IntelliJ IDEA or your preferred IDE.

Make sure JavaFX is configured properly.

Run the Main.java or EverGreenFinance.java file.

📄 License
This project was created for academic purposes at Farmingdale State College under the guidance of Prof. K. Moorning. Redistribution is permitted with credit.



1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/EverGreenFinance.git
   cd EverGreenFinance
