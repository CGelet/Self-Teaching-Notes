import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        if (scanner.hasNextInt()) {
            int number = scanner.nextInt();
            int result = number + 5;
            System.out.println("Result: " + result);
        } else {
            System.out.println("Error: Invalid input. Please enter a number.");
        }

        scanner.close();
    }
}
