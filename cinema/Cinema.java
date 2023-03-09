package cinema;

import java.util.Scanner;

public class Cinema {
    private static final int totalIncome = 0;
    private static char[][] cinema;
    private static boolean smallRoom = true;
    private static int rows;
    private static int seats;
    private static int currentIncome = 0;

    public Cinema() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        cinema = new char[rows + 1][seats + 1];
        if (rows * seats > 60) {
            smallRoom = false;
        }
        // first row
        for (int i = 0; i <= seats; i++) {
            cinema[0][i] = i > 0 ? (char) (i + '0') : ' ';
        }
        // first seat
        for (int i = 1; i <= rows; i++) {
            cinema[i][0] = (char) (i + '0');
        }
        for (int i = 1; i <= seats; i++) {
            for (int j = 1; j <= rows; j++) {
                cinema[j][i] = 'S';
            }
        }
    }

    private static int calculateTicketPrice(int row) {
        int ticketPrice = 10;
        if (!smallRoom && row > rows / 2) {
            ticketPrice = 8;
        }
        System.out.println("Ticket price: $" + ticketPrice);
        return ticketPrice;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cinema myCinema = new Cinema();
        int option;
        do {
            myCinema.showMenuOptions();
            option = scanner.nextInt();
            if (option == 1) {
                myCinema.showSeats();
            }
            if (option == 2) {
                myCinema.buyTicket();
            }
            if (option == 3) {
                myCinema.showStatistics();
            }
        } while (option > 0);
    }

    private int calculateTotalIncome() {
        if (totalIncome != 0) {
            return totalIncome;
        }
        if (smallRoom) {
            return 10 * rows * seats;
        } else {
            int frontRows = rows / 2;
            int backRows = rows - frontRows;
            return seats * (frontRows * 10 + backRows * 8);
        }
    }

    private void buyTicket() {
        Scanner scanner = new Scanner(System.in);
        boolean validCoords = false;
        int row = 0, seat = 0;
        while (!validCoords) {
            System.out.println();
            System.out.println("Enter a row number:");
            row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            seat = scanner.nextInt();
            // validate coords
            if (row < 0 || row > rows || seat < 0 || seat > seats) {
                System.out.println();
                System.out.println("Wrong input!");
                continue;
            }
            if (cinema[row][seat] == 'B') {
                System.out.println();
                System.out.println("That ticket has already been purchased!");
                continue;
            }
            validCoords = true;
        }
        cinema[row][seat] = 'B';
        currentIncome += calculateTicketPrice(row);
    }

    private void showMenuOptions() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Show statistics");
        System.out.println("0. Exit");
    }

    private void showSeats() {
        System.out.println();
        System.out.println("Cinema:");
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= seats; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int calculateNumberOfPurchasedTickets() {
        int tickets = 0;
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= seats; j++) {
                if (cinema[i][j] == 'B') {
                    tickets++;
                }
            }
        }
        return tickets;
    }

    private void showStatistics() {
        int soldTickets = calculateNumberOfPurchasedTickets();
        float percentage = (float) soldTickets / (rows * seats) * 100;
        System.out.println();
        System.out.printf("Number of purchased tickets: %d%n", soldTickets);
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%d%n", calculateTotalIncome());
    }
}