import command.Command;
import command.CreateNoteCommand;
import command.ExitCommand;
import command.ListNotesCommand;
import network.ApiClient;

import util.Config;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Command> commands = new HashMap<>();

    public static void main(String[] args) {
        // Load configuration from .env file
        Config.getInstance();

        ApiClient apiClient = new ApiClient();

        commands.put("1", new ListNotesCommand(apiClient));
        commands.put("2", new CreateNoteCommand(apiClient)); // Assuming CreateNoteCommand is defined
        commands.put("3", new ExitCommand());

        System.out.println("📒 Welcome to NoteManager CLI");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMenu();
            System.out.print("Choose: ");
            String input = scanner.nextLine();

            Command command = commands.get(input);
            if (command != null) {
                // cleanup screen before executing the command
                cleanup();
                command.execute();
            } else {
                System.out.println("❌ Invalid option.");
            }
        }
    }

    private static void cleanup() {
        // Clear the console (platform dependent)
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // If clearing fails, just print a new line
            System.out.println("\n".repeat(50));
        }
    }

    private static void showMenu() {
        System.out.println("\nOptions:");
        System.out.println("1. List Notes");
        System.out.println("2. Create Note");
        System.out.println("3. Exit");
        System.out.println("\nPlease select an option (1-3):");
    }
}
