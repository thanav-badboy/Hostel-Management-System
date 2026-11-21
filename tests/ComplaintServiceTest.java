import java.util.List;

/** Checks registration behavior through the complaint service API. */
public final class ComplaintServiceTest {
    public static void main(String[] args) throws DuplicateComplaintException {
        ComplaintService service = new ComplaintManager();
        Complaint registered = service.registerComplaint("C001", "S001", "Room", "No curtains");
        check(registered.getStatus() == Complaint.Status.PENDING,
                "A new complaint should start as Pending.");

        try {
            service.registerComplaint("C001", "S002", "Bathroom", "No heater");
            throw new AssertionError("A duplicate ID should throw DuplicateComplaintException.");
        } catch (DuplicateComplaintException expected) {
            // Check the stored record below to ensure the failed registration had no effect.
        }

        List<Complaint> complaints = service.viewComplaints();
        check(complaints.size() == 1, "Exactly one complaint should remain after a duplicate.");
        Complaint original = complaints.get(0);
        check("C001".equals(original.getComplaintId()), "The original complaint ID should remain.");
        check("S001".equals(original.getStudentId()), "The original student ID should remain.");
        check("Room".equals(original.getCategory()), "The original category should remain.");
        check("No curtains".equals(original.getDescription()), "The original description should remain.");
        check(original.getStatus() == Complaint.Status.PENDING, "The original status should remain.");

        System.out.println("PASS: Registration starts Pending and duplicate IDs preserve the original complaint.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
