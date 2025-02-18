import java.util.Scanner;

public class InputHandler {
    static Scanner scanner = new Scanner(System.in);

    public static int getPositiveInt(String text) {
        while (true) {
            System.out.println(text);
            if (scanner.hasNextInt()) {
                int result = scanner.nextInt();
                if (result >= 0) {
                    scanner.nextLine();
                    return result;
                }
            }
            scanner.nextLine();
            System.out.println("Please enter a valid positive integer!");
        }
    }

    public static String getString(String text) {
        while (true) {
            System.out.println(text);
            String result = scanner.nextLine();
            if (!result.isEmpty()) {
                return result;
            }
            System.out.println("Please enter a valid string! Can not be empty.");
        }
    }

    public static boolean getBoolean(String text){
        while(true){
            System.out.println(text);
            String strInput = scanner.nextLine();
            if(strInput.toLowerCase().startsWith("y")){
                return true;
            }
            if(strInput.toLowerCase().startsWith("n")){
                return false;
            }
            System.out.println("Please enter a valid choice. Y/N ");
        }
    }
}
