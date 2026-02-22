# 💸 Expense Splitter & Debt Tracker Web Application

A web-based expense management system built using **Java, Spring Boot (MVC), MySQL, HTML, and CSS** that helps users:

* Split group expenses based on custom percentages
* Track personal debts (who owes whom)
* Receive automated email reminders for pending payments

This project focuses on applying backend concepts like **MVC architecture, database relationships, business logic handling, and email automation** in a practical financial use case.

## 🚀 Features

### 1️⃣ Group Expense Splitter Mode

* User enters total number of participants
* Inputs:

  * Names of participants
  * Custom percentage share for each person
* System:

  * Validates percentage distribution
  * Calculates individual payable amounts
  * Displays final split result

✔ Handles real-time calculation

✔ Ensures total percentage equals 100%

✔ Prevents invalid inputs


### 2️⃣ Debt Tracking Mode (Who Owes Whom)

* User can:

  * Track how much they owe someone
  * Track how much someone owes them
* Stores:

  * Debtor
  * Creditor
  * Amount
* Displays structured debt records

✔ Persistent storage using MySQL

✔ Clean data retrieval using Spring MVC

✔ Structured financial tracking


### 3️⃣ Email Reminder System (In Progress)

* Automated email reminders for:

  * Pending debts
  * Due payments
* Will be implemented using:

  * Spring Boot Mail API
  * Scheduled background tasks


## 🛠 Tech Stack

| Layer         | Technology Used                   |
| ------------- | --------------------------------- |
| Backend       | Java                              |
| Framework     | Spring Boot (MVC Architecture)    |
| Database      | MySQL                             |
| Frontend      | HTML, CSS                         |
| Email Service | Spring Boot Mail (JavaMailSender) |


## 🧮 Core Functional Logic

### Group Split Calculation

* Validates total percentage
* Converts percentage into monetary distribution
* Returns individual payable values

### Debt Tracking

* Stores creditor-debtor relationship
* Retrieves balance records
* Maintains transaction consistency in database

## 📌 Current Status

✅ Expense split calculation completed

✅ Debt tracking completed, where user can get teh total remaining and total payment done, can dynamically update if the payment is paid or pending

🚧 Email reminder automation in progress


## 💡 Learning Outcomes

This project strengthened understanding of:

* MVC architecture in Spring Boot
* Backend financial logic handling
* MySQL relational database design
* Form handling and validation
* Email automation using Java

