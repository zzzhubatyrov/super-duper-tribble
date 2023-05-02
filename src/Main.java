import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.LinkedList;


public class Main {
    protected static LinkedList<File> dirFile = new LinkedList<>();
    protected static LinkedList<String> linkedMenu = new LinkedList<>();
    protected static Scanner sc = new Scanner(System.in);
    protected static String path;
    protected static String folderName;
    protected static String allPath;

    public static void main(String[] args) {
        try {
            mainMenu();
        } catch (Exception e) {
            // clearScreen();
        }
    }

    public static void mainMenu() throws IOException, InterruptedException {
        LinkedList<String> linkedMenu = new LinkedList<>();
        System.out.println("This is Main Menu: ");
        System.out.println("(Entry \\c for clear console)");

        System.out.print("Entry PATH: ");
        path = sc.next() + "\\";
        linkedMenu.add(path);
        for (var p: linkedMenu) {
            System.out.println("PATH: " + p + '\n');
        }
        System.out.println("1: Create Folder\n2: Search & Copy Files");

        int swMenu = sc.nextInt();
        sc.nextLine(); // clear buffer
        switch (swMenu) {
            case 1:
                System.out.print("CREATE FOLDER: \nEntry Folder Name: ");
                folderName = sc.next() + "\\";
                allPath = linkedMenu.get(0);
                createFolder(allPath, folderName);
                break;
            case 2:
                System.out.print("ENTRY DIR FOR SEARCH FILES (PRESS ENTER TO SEARCH FROM CURRENT DIRECTORY): ");
                if (sc.hasNextLine()) {     // проверяем наличие строки в буфере ввода
                    folderName = sc.nextLine();
                } else {
                    folderName = "";        // используем пустую строку, если ввода не было
                }
                linkedMenu.add(folderName);
                allPath = linkedMenu.get(0) + linkedMenu.get(1);
                System.out.print("ENTRY TYPE FILES: ");
                String typeOfFile = sc.nextLine();
                System.out.print("Do you want to see the files in the directory? (yes/no): ");
                String answer = sc.nextLine();
                if (answer.equals("yes")) {
                    // просмотреть файлы в директории
                    viewFilesInDirectory(allPath, typeOfFile);
                } else {
                    // только поиск файлов
                    searchFiles(allPath, typeOfFile);
                }
                break;
            case 3:
                break;
        }
    }

    public static void createFolder(String dir, String folderName) {
        linkedMenu.add(folderName);
        allPath = dir + folderName;
        File exFolder = new File(allPath);
        if (exFolder.exists()) {
            System.out.println(exFolder.getName() + " exists!");
        } else {
            new File(allPath).mkdir();
        }
    }

    public static void searchFiles(String dir, String typeOfFile) {
        File folder = new File(dir);
        File[] files = folder.listFiles();
        for (File file: files) {
            if (file.isDirectory()) {
                searchFiles(String.valueOf(file), typeOfFile);
            } else if (file.getName().toLowerCase().contains(typeOfFile.toLowerCase())) {
                dirFile.add(new File(file.getAbsolutePath()));
            }
        }
    }
    public static void viewFilesInDirectory(String path, String typeOfFile) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        for (File file: files) {
            if (file.isDirectory()) {
                viewFilesInDirectory(String.valueOf(file), typeOfFile);
            } else if (file.getName().toLowerCase().contains(typeOfFile.toLowerCase())) {
                dirFile.add(new File(file.getAbsolutePath()));
                getFiles(files);
                break;
            }
        }
    }
    public static void getFiles(File[] files) {
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i]);
        }
    }
    public static void copyFiles(String dir, String typeOfFile, String destDir) throws IOException {
        File folder = new File(dir);
        File[] files = folder.listFiles();
        for (File file: files) {
            if (file.isDirectory()) {
                copyFiles(String.valueOf(file), typeOfFile, destDir);
            } else if (file.getName().endsWith(typeOfFile)) {
                Path sourcePath = Paths.get(file.getAbsolutePath());
                Path destPath = Paths.get(destDir + "\\" + file.getName());
                Files.copy(sourcePath, destPath);
            }
        }
    }

    public static void testFunc() {
        for (var f: dirFile) {
            System.out.println(f);
        }
    }

//    public static void clearScreen() {
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//    }
}
