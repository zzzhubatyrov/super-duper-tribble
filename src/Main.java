import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.LinkedList;


public class Main {
    protected static LinkedList<File> dirFile = new LinkedList<>();
    protected static LinkedList<String> linkedMenu = new LinkedList<String>();
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
        switch (swMenu) {
            case 1:
                System.out.print("CREATE FOLDER: \nEntry Folder Name: ");
                folderName = sc.next() + "\\";
                allPath = linkedMenu.get(0);
                createFolder(allPath, folderName);
                break;
            case 2:
                System.out.print("ENTRY DIR FOR SEARCH FILES: ");
                folderName = sc.next() + "\\";
                linkedMenu.add(folderName);
                allPath = linkedMenu.get(0) + linkedMenu.get(1);
                System.out.print("ENTRY TYPE FILES: ");
                String typeOfFile = sc.next();
//                System.out.print("ENTRY DEST DIR: ");
//                String destDir = sc.next();
                searchFiles(allPath, typeOfFile);
                Thread.sleep(5000);
                // destDir: "C:\\Users\\zen\\Desktop\\rw2\\"
                testFunc();
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
            } else if (file.getName().endsWith(typeOfFile)) {
                dirFile.add(new File(file.getAbsolutePath()));
            }
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
