# Train Management System

This is a simple console-based Train Management System built in Java. It allows Admins and Customers to manage trains and bookings via a command-line interface.

---

# Project Structure

```
TrainManagementSystem/
├── src/
│   └── com/bnpp/wm/actimize/
│       ├── TrainMgmtApp.java
│       ├── model/
│       │   └── (Train.java, Customer.java, Booking.java, etc.)
│       ├── service/
│       │   └── (TrainService.java, BookingService.java, etc.)
│       └── enums/
│           └── (TrainType.java, TicketStatus.java, etc.)
├── out/ (compiled class files)
└── README.md
```

---

# Technologies Used

- Java (JDK 8 or above)
- Object-Oriented Programming
- Command-Line Interface (CLI)

---

# How to Compile and Run

1. **Compile the project** from the `src` folder:
   ```bash
   javac -d ../out com/bnpp/wm/actimize/*.java com/bnpp/wm/actimize/service/*.java com/bnpp/wm/actimize/model/*.java com/bnpp/wm/actimize/enums/*.java
   ```

2. **Run the application**:
   ```bash
   java -cp ../out com.bnpp.wm.actimize.TrainMgmtApp
   ```

---

# Features

# Admin Panel:
- Add a Train
- View All Trains
- Remove a Train

# ustomer Panel:
- View Available Trains
- Book a Ticket
- Cancel a Ticket

---

# Sample Output

```
Welcome to the Train Management System
1. Admin Panel
2. Customer Panel
3. Exit
```

---

# Author

**Kavya Reddy**  
BCA Graduate | Bangalore, India

---

# License

This project is free and open-source for learning and educational use.
