import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.*;
import java.util.List;

public class DiaryManager {
    private ArrayList<DiaryEntry> entries;

    // Constructor
    public DiaryManager() {
        this.entries = new ArrayList<>();
    }

    /*
        CRUD operations
            1. Add a new diary entry
            2. View all entries for a given year and month
            3. Delete an entry by date and author
            4. Update an existing diary entry
     */

    // 1. Add a new diary entry
    public void addEntry(DiaryEntry entry) {
        String filePath = generateFilePath(entry.getDate(), entry.getAuthor());
        saveToFile(filePath, entry);
    }

    // 2. View all entries for a given year and month
    public void viewEntries(String year, String month) {
        List<DiaryEntry> entries = loadEntriesForMonth(year, month);

        if (entries.isEmpty()) {
            System.out.println("❌ No entries found for " + year + " " + month);
        } else {
            System.out.println("\n📜 Entries for " + year + "-" + month + ":");
            entries.forEach(System.out::println);
        }

    }

    // 3. Delete an entry by date and author
    public void deleteEntry(LocalDate date, String author) {
        String filePath = generateFilePath(date, author);
        File file = new File(filePath);

        if (file.exists() && file.delete()) {
            System.out.println("✅ Entry deleted successfully.");
        } else {
            System.out.println("❌ Failed to delete entry. File not found.");
        }
    }

    // 4. Update an existing diary entry
    public void updateEntry(LocalDate date, String author, String newTitle, String newContent) {
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



    /*
        File operations:
            1. Generate a unique file path for a diary entry
            2. Save a diary entry to a file
            3. Load a diary entry from a file
            4. Load all entries for a given year and month
     */

    // 1.Generate a unique file path for a diary entry
    private String generateFilePath(LocalDate date, String author) {
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String year = date.format(yearFormatter);
        String month = date.format(monthFormatter);
        String day = date.format(dayFormatter);

        String fileName = day + "_" + author.replace(" ", "_") + ".ser";
        return "Diary/" + year + "/" + month + "/" + fileName;

    }

    // 2.Save a diary entry to a file
    private void saveToFile(String filePath, DiaryEntry entry) {
        try {
            // Create the file and necessary directories
            File file = new File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            // Use ObjectOutputStream to serialize the DiaryEntry object
            try (FileOutputStream fos = new FileOutputStream(file);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(entry);
                oos.flush();
            }

            System.out.println("✅ File saved: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Error saving entry: " + e.getMessage());
        }
    }

    // 3.Load a diary entry from a file
    private DiaryEntry loadFromFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (DiaryEntry) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠️ Error loading entry: " + e.getMessage());
            return null;
        }
    }

    // 4.Load all entries for a given year and month
    private List<DiaryEntry> loadEntriesForMonth(String year, String month) {
        String dirPath = "Diary/" + year + "/" + month;
        File directory = new File(dirPath);
        List<DiaryEntry> entries = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
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
