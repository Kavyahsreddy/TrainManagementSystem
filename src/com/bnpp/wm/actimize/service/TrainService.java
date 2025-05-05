package com.bnpp.wm.actimize.service;

import com.bnpp.wm.actimize.enums.TrainType;
import com.bnpp.wm.actimize.model.Customer;
import com.bnpp.wm.actimize.model.Order;
import com.bnpp.wm.actimize.model.Train;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TrainService {
    private final List<Train> trains = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private int orderCounter = 1;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("Welcome to the Train Management System\n1. Admin Panel\n2. Customer Panel\n3. Exit");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> adminPanel();
                case 2 -> customerPanel();
                case 3 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    public void adminPanel() {
        while (true) {
            System.out.println("\nAdmin Panel\n1. Add Train\n2. Delete Train\n3. Back");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addTrain();
                case 2 -> deleteTrain();
                case 3 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    public void customerPanel() {
        try {
            System.out.print("Enter Customer ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter DOB (dd-MM-yyyy): ");
            String dobStr = scanner.nextLine();

            Date dob;
            try {
                dob = new SimpleDateFormat("dd-MM-yyyy").parse(dobStr);
            } catch (Exception e) {
                System.out.println("Invalid date. Using current date.");
                dob = new Date();
            }

            Customer customer = new Customer(id, name, dob);

            while (true) {
                System.out.println("\nCustomer Panel\n1. View All Trains\n2. Search Trains\n3. Book Train\n4. Back");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input.");
                    scanner.nextLine();
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> viewAllTrains();
                    case 2 -> searchTrains();
                    case 3 -> bookTrain(customer);
                    case 4 -> { return; }
                    default -> System.out.println("Invalid option");
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong. Returning to menu.");
        }
    }

    private void addTrain() {
        try {
            System.out.print("Enter Train Number: ");
            int number = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Train Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Source: ");
            String source = scanner.nextLine();

            System.out.print("Enter Destination: ");
            String destination = scanner.nextLine();

            System.out.print("Enter Sub Stops (comma-separated): ");
            List<String> subStops = List.of(scanner.nextLine().split(","));

            System.out.print("Enter Train Type (FULL_AC, SUPERFAST, EXPRESS): ");
            TrainType type = TrainType.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Total Seats: ");
            int seats = Integer.parseInt(scanner.nextLine());

            trains.add(new Train(number, name, source, destination, subStops, type, price, seats));
            System.out.println("Train added successfully.");
        } catch (Exception e) {
            System.out.println("Invalid input. Try again.");
        }
    }

    private void deleteTrain() {
        System.out.print("Enter Train Number to Delete: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid number.");
            scanner.nextLine();
            return;
        }

        int number = scanner.nextInt();
        scanner.nextLine();

        boolean removed = trains.removeIf(t -> t.trainNumber == number);
        System.out.println(removed ? "Train deleted." : "Train not found.");
    }

    private void viewAllTrains() {
    if (trains.isEmpty()) {
        System.out.println("No trains available.");
        return;
    }

    System.out.println("View Trains:\n1. Sort by Train Number\n2. Sort by Train Name");
    if (!scanner.hasNextInt()) {
        System.out.println("Invalid input.");
        scanner.nextLine();
        return;
    }

    int sortChoice = scanner.nextInt();
    scanner.nextLine();

    switch (sortChoice) {
        case 1 -> trains.sort((t1, t2) -> Integer.compare(t1.trainNumber, t2.trainNumber));
        case 2 -> trains.sort((t1, t2) -> t1.trainName.compareToIgnoreCase(t2.trainName));
        default -> {
            System.out.println("Invalid choice. Showing unsorted list.");
        }
    }

    System.out.println("Trains:");
    trains.forEach(System.out::println);
}


    private void searchTrains() {
        System.out.print("Enter Source: ");
        String source = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String dest = scanner.nextLine();

        boolean found = false;
        for (Train t : trains) {
            if (t.source.equalsIgnoreCase(source) && t.destination.equalsIgnoreCase(dest)) {
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No trains found.");
        }
    }

    private void bookTrain(Customer customer) {
        System.out.print("Enter Train Number to Book: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid number.");
            scanner.nextLine();
            return;
        }

        int trainNo = scanner.nextInt();
        scanner.nextLine();

        Train train = null;
        for (Train t : trains) {
            if (t.trainNumber == trainNo) {
                train = t;
                break;
            }
        }

        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        System.out.print("Enter number of tickets: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid ticket count.");
            scanner.nextLine();
            return;
        }

        int tickets = scanner.nextInt();
        scanner.nextLine();

        if (tickets > train.totalSeats) {
            System.out.println("Not enough seats.");
        } else {
            double total = tickets * train.price;
            train.totalSeats -= tickets;
            Order order = new Order(orderCounter++, customer.id, tickets, total, train.trainNumber, train.trainName);
            orders.add(order);
            System.out.println("Booking successful: " + order);
        }
    }
}
