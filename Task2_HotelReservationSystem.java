import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

class Reservation {
    private Room room;
    private String customerName;
    private int numberOfNights;
    private double totalCost;

    public Reservation(Room room, String customerName, int numberOfNights) {
        this.room = room;
        this.customerName = customerName;
        this.numberOfNights = numberOfNights;
        this.totalCost = room.getPrice() * numberOfNights;
        room.setAvailable(false);
    }

    public Room getRoom() {
        return room;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "room=" + room +
                ", customerName='" + customerName + '\'' +
                ", numberOfNights=" + numberOfNights +
                ", totalCost=" + totalCost +
                '}';
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public Hotel() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> searchAvailableRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void makeReservation(String customerName, String category, int numberOfNights) {
        List<Room> availableRooms = searchAvailableRooms(category);
        if (!availableRooms.isEmpty()) {
            Room room = availableRooms.get(0);
            Reservation reservation = new Reservation(room, customerName, numberOfNights);
            reservations.add(reservation);
            System.out.println("Reservation successful!");
            System.out.println(reservation);
        } else {
            System.out.println("No available rooms in the " + category + " category.");
        }
    }

    public void viewReservations() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public void processPayment(Reservation reservation) {
        System.out.println("Processing payment for " + reservation.getCustomerName());
        System.out.println("Total cost: " + reservation.getTotalCost());
        System.out.println("Payment successful!");
    }
}

public class Main {
    public static void main(String args[]) {
        Hotel h = new Hotel();
        h.addRoom(new Room(101, "Single", 100.0));
        h.addRoom(new Room(102, "Double", 150.0));
        h.addRoom(new Room(103, "Suite", 200.0));
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Search available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. View reservations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume the leftover newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter room category to search: ");
                    String category = sc.nextLine();
                    List<Room> availableRooms = h.searchAvailableRooms(category);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No available rooms in the " + category + " category.");
                    } else {
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String customerName = sc.nextLine();
                    System.out.print("Enter room category: ");
                    String roomCategory = sc.nextLine();
                    System.out.print("Enter number of nights: ");
                    int numberOfNights = sc.nextInt();
                    sc.nextLine(); // consume the leftover newline character
                    h.makeReservation(customerName, roomCategory, numberOfNights);
                    break;
                case 3:
                    h.viewReservations();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
