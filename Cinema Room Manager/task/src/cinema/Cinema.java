package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static Scanner scanner = new Scanner(System.in);
    public static int ticketPrizeSum = 0;
    public static int ticketCount = 0;
    public static int totalIncome = 0;

    public static void main(String[] args) {
        int option = 0;

        System.out.println("Enter the number of rows:");
        int seatRow = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatColumn = scanner.nextInt();
        int[] seatSize = {seatRow + 1, seatColumn + 1};
        String[][] seatArray = createSeats(seatSize);
        totalSeatIncome(seatColumn, seatRow);
        do {
            System.out.println("1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit");
            option = scanner.nextInt();

            switch (option) {
                case 0:
                    break;
                case 1:
                    printSeats(seatArray);
                    break;
                case 2:
                    buyTicket(seatColumn, seatRow, seatArray);
                    break;
                case 3:
                    statistic(seatRow, seatColumn);
            }

        } while (option != 0);
    }

    public static void buyTicket(int seatColumn, int seatRow, String[][] seatArray) {
        int rows;
        int seat;
        boolean booked = false;
        do {
            do {
                System.out.println("Enter a row number:");
                rows = scanner.nextInt();

                System.out.println("Enter a seat number in that row:");
                seat = scanner.nextInt();

                if ((rows <= 0 || rows > seatRow) || (seat <= 0 || seat > seatColumn)) {
                    System.out.println("Wrong input!");
                }
            }
            while ((rows <= 0 || rows > seatRow) || (seat <= 0 || seat > seatColumn));
            if (setBooked(seatArray, rows, seat)) {
                ticketCost(rows, seatColumn, seatRow);
                booked = false;
            } else {
                booked = true;
                System.out.println("That ticket has already been purchased!");
            }
        } while (booked == true);
    }

    public static String[][] createSeats(int[] seatSize) {
        String[][] seatArray = new String[seatSize[0]][seatSize[1]];
        for (int i = 0; i < seatArray.length; i++) {
            for (int j = 0; j < seatArray[i].length; j++) {
                seatArray[i][j] = "S";
            }
        }
        seatArray[0][0] = " ";
        for (int i = 1; i < seatArray[0].length; i++) {
            seatArray[0][i] = String.valueOf(i);
        }
        for (int i = 1; i < seatArray.length; i++) {
            seatArray[i][0] = String.valueOf(i);
        }
        return seatArray;
    }

    public static Boolean setBooked(String[][] seatArray, int row, int seat) {
        if (seatArray[row][seat].equals("B")) {
            return false;
        }
        seatArray[row][seat] = "B";
        return true;
    }

    public static void printSeats(String[][] seatArray) {
        System.out.println("Cinema:\n" + Arrays.deepToString(seatArray).replace("], ", "\n").replace("[", "").replace("[[", " ").replace("]]", "").replace(",", ""));
    }

    public static void ticketCost(int rows, int seat, int seatRow) {
        int ticketPrice;
        int totalSeats = seatRow * seat;
        int frontRow = seatRow / 2;
        if (totalSeats <= 60) {
            ticketPrice = 10;
        } else if (rows <= frontRow) {
            ticketPrice = 10;
        } else {
            ticketPrice = 8;
        }
        ticketPrizeSum += ticketPrice;
        ticketCount++;
        System.out.println("Ticket price: $" + ticketPrice);
    }

    public static void statistic(int seatRow, int seatColumn) {
        System.out.println("Number of purchased tickets: " + ticketCount +
                "\nPercentage: " + String.format("%.2f", percentageOfTickets(seatRow, seatColumn)) + "%" +
                "\nCurrent income: $" + ticketPrizeSum +
                "\nTotal income: $" + totalIncome);
    }

    public static double percentageOfTickets(int seatRow, int seatColumn) {
        return (double) (ticketCount * 100) / (seatRow * seatColumn);
    }

    public static void totalSeatIncome(int seatColumn, int seatRow) {
        int totalSeats = seatRow * seatColumn;
        int frontRow = seatRow / 2;
        if (totalSeats <= 60) {
            totalIncome = totalSeats * 10;
        } else {
            totalIncome = (frontRow * seatColumn) * 10 + ((seatRow - frontRow) * seatColumn) * 8;
        }
    }
}