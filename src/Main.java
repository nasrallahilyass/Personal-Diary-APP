import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DiaryManager diaryManager = new DiaryManager();

        /*
        // Initial entries for testing:
        diaryManager.addEntry(new DiaryEntry(LocalDate.now(), "Today's Diary", "Today I learned about constructors in Java", "John Doe"));
        diaryManager.addEntry(new DiaryEntry(LocalDate.now().minusDays(1), "Yesterday's Diary", "Yesterday I learned about getters and setters in Java", "John Doe"));
        diaryManager.addEntry(new DiaryEntry(LocalDate.now().minusDays(2), "Day Before Yesterday's Diary", "Day Before Yesterday I learned about Java methods", "John Doe"));
        diaryManager.addEntry(new DiaryEntry(LocalDate.now().minusDays(3), "3 Days Ago Diary", "3 Days Ago I learned about Java classes", "John Doe"));
        diaryManager.addEntry(new DiaryEntry(LocalDate.now().minusDays(4), "4 Days Ago Diary", "4 Days Ago I learned about Java variables", "John Doe"));
        */

        while (true) {
            printMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1 -> addEntry(scanner, diaryManager);
                case 2 -> viewEntries(scanner, diaryManager);
                case 3 -> searchEntry(scanner, diaryManager);
                case 4 -> editEntry(scanner, diaryManager);
                case 5 -> deleteEntry(scanner, diaryManager);
                case 6 -> {
                    System.out.println("\nThank you for using the Diary Manager. Goodbye!");
                    return;
                }
                default -> System.out.println("❌ Invalid option! Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                DIARY MANAGER");
        System.out.println("=".repeat(50));
        System.out.println("1. Add Entry");
        System.out.println("2. View Entries");
        System.out.println("3. Search Entry");
        System.out.println("4. Edit Entry");
        System.out.println("5. Delete Entry");
        System.out.println("6. Exit");
        System.out.println("=".repeat(50));
    }

    private static int getUserChoice(Scanner scanner) {
        try {
            System.out.print("Choose an option: ");
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addEntry(Scanner scanner, DiaryManager diaryManager) {
        System.out.println("\n--- Add a New Entry ---");
        LocalDate date = getValidDate(scanner, "Enter date (yyyy-MM-dd): ");
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter content: ");
        String content = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        diaryManager.addEntry(new DiaryEntry(date, title, content, author));
        System.out.println("\n✅ Entry added successfully!");
    }

    private static void viewEntries(Scanner scanner, DiaryManager diaryManager) {
        System.out.println("\n--- View Entries ---");
        System.out.print("Enter year (e.g., 2024): ");
        String year = scanner.nextLine();
        System.out.print("Enter month (e.g., 11): ");
        String month = scanner.nextLine();
        diaryManager.viewEntries(year, month);
    }

    private static void searchEntry(Scanner scanner, DiaryManager diaryManager) {
        System.out.println("\n--- Search for an Entry ---");
        LocalDate date = getValidDate(scanner, "Enter date (yyyy-MM-dd): ");
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        DiaryEntry entry = diaryManager.searchEntry(date, author);
        if (entry != null) {
            System.out.println("\n🎯 Entry found:\n" + entry);
        } else {
            System.out.println("\n❌ No entry found.");
        }
    }

    private static void editEntry(Scanner scanner, DiaryManager diaryManager) {
        System.out.println("\n--- Edit an Entry ---");
        LocalDate date = getValidDate(scanner, "Enter date of entry to edit (yyyy-MM-dd): ");
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new title: ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new content: ");
        String newContent = scanner.nextLine();
        diaryManager.editEntry(date, author, newTitle, newContent);
    }

    private static void deleteEntry(Scanner scanner, DiaryManager diaryManager) {
        System.out.println("\n--- Delete an Entry ---");
        LocalDate date = getValidDate(scanner, "Enter date of entry to delete (yyyy-MM-dd): ");
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        diaryManager.deleteEntry(date, author);
    }

    private static LocalDate getValidDate(Scanner scanner, String prompt) {
        LocalDate date = null;
        while (date == null) {
            try {
                System.out.print(prompt);
                date = LocalDate.parse(scanner.nextLine(), dateFormatter);
            } catch (Exception e) {
                System.out.println("❌ Invalid date format! Please try again.");
            }
        }
        return date;
    }
}
