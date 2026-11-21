import com.hostel.student.main.StudentMain;
import java.util.Scanner;

/**
 * Unified Console Application entry point for the College Hostel Management System.
 * Connects Member 1 (Student Management) and Member 2 (Room Management).
 */
public class UnifiedHostelApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=======================================================");
            System.out.println("       COLLEGE HOSTEL MANAGEMENT SYSTEM - MAIN HUB     ");
            System.out.println("=======================================================");
            System.out.println(" 1. Student Management Module (Member 1)");
            System.out.println(" 2. Room Management Module (Member 2)");
            System.out.println(" 3. View System Integration Architecture");
            System.out.println(" 4. Exit");
            System.out.println("=======================================================");
            System.out.print("Select module to launch [1-4]: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println("\n>>> Launching Student Management Module...\n");
                    StudentMain.main(new String[]{});
                    break;
                case "2":
                    System.out.println("\n>>> Launching Room Management Module...\n");
                    Main.main(new String[]{});
                    break;
                case "3":
                    displayArchitecture();
                    break;
                case "4":
                    System.out.println("\nShutting down College Hostel Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("[ERROR] Invalid choice. Please choose 1, 2, 3, or 4.");
            }
        }
    }

    private static void displayArchitecture() {
        System.out.println("\n--- HOSTEL SYSTEM INTEGRATION ARCHITECTURE ---");
        System.out.println(" • Common Identifier: studentId (e.g. STU101)");
        System.out.println(" • Member 1: Student Management (Identity, Profiles, Validation, Custom DSA)");
        System.out.println(" • Member 2: Room Management (Rooms, Bed Capacities, MergeSort, Binary Search)");
        System.out.println(" • Member 3: Fee Management (Dues, Invoices, Payment Ledger)");
        System.out.println(" • Member 4: Complaint Management (Grievances, Status Tracking)");
        System.out.println("----------------------------------------------");
    }
}
