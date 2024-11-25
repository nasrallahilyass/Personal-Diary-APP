import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DiaryManager {
    private List<DiaryEntry> entries;

    // Constructors:
    public DiaryManager() {
        this.entries = new ArrayList<>();
    }

    /*
        **********************
        Public Methods:
        **********************
     */

    // Add a new diary entry
    public void addEntry(DiaryEntry entry) {
        String filePath = generateFilePath(entry.getDate(), entry.getAuthor());
        saveToFile(filePath, entry);
    }

    // Edit an existing diary entry
    public void editEntry(LocalDate date, String author, String newTitle, String newContent) {
        String filePath = generateFilePath(date, author);
        DiaryEntry entry = loadFromFile(filePath);
        if (entry != null) {
            entry.setTitle(newTitle);
            entry.setContent(newContent);
            saveToFile(filePath, entry);
            System.out.println("✅ Entry updated successfully.");
        } else {
            System.out.println("❌ Entry not found for the given date and author.");

        }
    }

    // View all entries for a given year and month
    public void viewEntries(String year, String month) {
        List<DiaryEntry> entries = loadEntriesForMonth(year, month);

        if (entries.isEmpty()) {
            System.out.println("❌ No entries found for " + year + " " + month);
        } else {
            System.out.println("\n📜 Entries for " + year + "-" + month + ":");
            entries.forEach(System.out::println);
        }

    }

    // Search for an entry by date and author
    public DiaryEntry searchEntry(LocalDate date, String author){
        String filePath = generateFilePath(date, author);
        return loadFromFile(filePath);
    }

    // Delete an entry by date and author
    public void deleteEntry(LocalDate date, String author) {
        String filePath = generateFilePath(date, author);
        File file = new File(filePath);
        if (file.exists() && file.delete()) {
            System.out.println("✅ Entry deleted successfully.");
        } else {
            System.out.println("❌ Failed to delete entry. File not found.");
        }


    }

    /*
        **********************
        Private Helper Methods:
        **********************
     */

    // Generate a unique file path for a diary entry
    private String generateFilePath(LocalDate date, String author) {
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String year = date.format(yearFormatter);
        String month = date.format(monthFormatter);
        String day = date.format(dayFormatter);

        String fileName = day + "_" + author.replace(" ", "_") + ".txt";

        return "Diary/" + year + "/" + month + "/" + fileName;

    }

    // Save an entry to a file
    private void saveToFile(String filePath, DiaryEntry entry) {
        try {
            //✅ filePath: Diary/2024/11/2024-11-23_John_Doe.txt
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Date: " + entry.getDate() + "\n");
                writer.write("Title: " + entry.getTitle() + "\n");
                writer.write("Content: " + entry.getContent() + "\n");
                writer.write("Author: " + entry.getAuthor() + "\n");

            }
            System.out.println("✅ File saved: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Error saving entry: " + e.getMessage());
        }
    }

    // Read a file containing details of a DiaryEntry and return a DiaryEntry object
    private DiaryEntry loadFromFile(String filePath) {
        //✅ filePath: Diary/2024/11/2024-11-23_John_Doe.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            LocalDate date = LocalDate.parse(reader.readLine().split(": ", 2)[1]); //["Date", "2024-11-23"] && [1]: "2024-11-23"
            String title = reader.readLine().split(": ", 2)[1];
            String content = reader.readLine().split(": ", 2)[1];
            String author = reader.readLine().split(": ", 2)[1];
            return new DiaryEntry(date, title, content, author);

        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.err.println("⚠️ Error loading entry: " + e.getMessage());
            return null;
        }
    }

    // Load all entries for a given year and month
    private List<DiaryEntry> loadEntriesForMonth(String year, String month) {
        String dirPath = "Diary/" + year + "/" + month;
        File directory = new File(dirPath);
        List<DiaryEntry> entries = new ArrayList<>();
        if (directory.exists() && directory.isDirectory()) {
            // A filter is applied to include only files ending with .txt.
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

            if (files != null) {
                for (File file : files) {
                    DiaryEntry entry = loadFromFile(file.getAbsolutePath());
                    if (entry != null) {
                        entries.add(entry);
                    }
                }
            }
        }
        return entries;
    }

}
