/** Indicates that no complaint exists with the requested ID. */
public class ComplaintNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /** Creates an exception identifying the missing complaint. */
    public ComplaintNotFoundException(String complaintId) {
        super("Complaint not found: " + complaintId);
    }
}
