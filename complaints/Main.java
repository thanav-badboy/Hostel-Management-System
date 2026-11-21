import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Runs the standalone complaint management console. */
public final class Main {
    private Main() {
    }

    /** Starts the console menu with an empty complaint manager. */
    public static void main(String[] args) {
        ComplaintService service = new ComplaintManager();
        try (Scanner input = new Scanner(System.in)) {
            runMenu(input, service);
        } catch (NoSuchElementException endOfInput) {
            System.out.println("Input closed. Exiting complaint management.");
        }
    }

    private static void runMenu(Scanner input, ComplaintService service) {
        while (true) {
            printMenu();
            String choice = prompt(input, "Choose an option: ");
            try {
                switch (choice) {
                    case "1":
                        service.registerComplaint(prompt(input, "Complaint ID: "),
                                prompt(input, "Student ID: "), prompt(input, "Category: "),
                                prompt(input, "Description: "));
                        System.out.println("Complaint registered with status Pending.");
                        break;
                    case "2":
                        printComplaints(service.viewComplaints(), "No complaints registered.");
                        break;
                    case "3":
                        printComplaint(service.searchComplaint(prompt(input, "Complaint ID: ")));
                        break;
                    case "4":
                        String id = prompt(input, "Complaint ID: ");
                        Complaint updated = service.updateComplaintStatus(id, readStatus(input));
                        System.out.println("Status updated to " + updated.getStatus() + ".");
                        break;
                    case "5":
                        service.deleteComplaint(prompt(input, "Complaint ID: "));
                        System.out.println("Complaint deleted.");
                        break;
                    case "6":
                        printComplaints(service.getComplaintHistory(prompt(input, "Student ID: ")),
                                "No complaints found for that student.");
                        break;
                    case "0":
                        System.out.println("Exiting complaint management.");
                        return;
                    default:
                        System.out.println("Invalid menu option. Choose 0 to 6.");
                }
            } catch (ComplaintNotFoundException | DuplicateComplaintException
                     | IllegalArgumentException error) {
                System.out.println("Error: " + error.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== Complaint Management ===");
        System.out.println("1. Register Complaint");
        System.out.println("2. View Complaints");
        System.out.println("3. Search Complaint");
        System.out.println("4. Update Complaint Status");
        System.out.println("5. Delete Complaint");
        System.out.println("6. Complaint History");
        System.out.println("0. Exit");
    }

    private static Complaint.Status readStatus(Scanner input) {
        System.out.println("1. Pending");
        System.out.println("2. In Progress");
        System.out.println("3. Resolved");
        switch (prompt(input, "Choose a status: ")) {
            case "1":
                return Complaint.Status.PENDING;
            case "2":
                return Complaint.Status.IN_PROGRESS;
            case "3":
                return Complaint.Status.RESOLVED;
            default:
                throw new IllegalArgumentException("Invalid status option. Choose 1 to 3.");
        }
    }

    private static String prompt(Scanner input, String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }

    private static void printComplaints(List<Complaint> complaints, String emptyMessage) {
        if (complaints.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Complaint complaint : complaints) {
            printComplaint(complaint);
        }
    }

    private static void printComplaint(Complaint complaint) {
        System.out.println();
        System.out.println("Complaint ID: " + complaint.getComplaintId());
        System.out.println("Student ID: " + complaint.getStudentId());
        System.out.println("Category: " + complaint.getCategory());
        System.out.println("Description: " + complaint.getDescription());
        System.out.println("Status: " + complaint.getStatus());
    }
}
