import java.util.Scanner;

public class UnProgram {
    public static void main(String[] args) {

        var scanner = new Scanner(System.in);

        System.out.print("a = ");
        int a = Integer.parseInt(scanner.nextLine());

        System.out.print("b = ");
        int b = Integer.parseInt(scanner.nextLine());

        System.out.print("Nume: ");
        var nume = scanner.nextLine();
        System.out.printf("Hello %s!%n", nume);
        System.out.printf("%d + %d = %d", a, b, a+b);

    }
}